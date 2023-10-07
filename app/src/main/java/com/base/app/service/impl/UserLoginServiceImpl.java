package com.base.app.service.impl;

import com.base.app.config.encrypt.RequestWrapper;
import com.base.app.config.encrypt.TokenConfig;
import com.base.app.config.sys.SysConfig;
import com.base.app.constants.LoginConstants;
import com.base.app.constants.UserConstants;
import com.base.app.dto.user.LoginTokenDTO;
import com.base.app.dto.user.LoginUserDTO;
import com.base.app.error.LoginErrorCode;
import com.base.app.ro.login.UserLoginRo;
import com.base.app.service.*;
import com.base.app.util.PasswordUtils;
import com.base.app.vo.sys.UserAccountVo;
import com.base.app.vo.sys.UserLoginLogVo;
import com.base.app.vo.sys.UserLoginVo;
import com.base.app.vo.sys.UserVo;
import com.base.core.context.CloudManager;
import com.base.core.exception.CloudException;
import com.base.util.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Resource
    private CaptchaService captchaService;
    @Resource
    private UserService userService;
    @Resource
    private TokenService tokenService;
    @Resource
    private RedisService redisService;
    @Resource
    private SysConfig sysConfig;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public LoginTokenDTO userLogin(Map<String, String> parameters) {
        // 转换请求参数为登陆参数
        UserLoginRo userLoginRo = JsonUtils.createBean(parameters, UserLoginRo.class);
        userLoginRo.setLastLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        userLoginRo.setLastLoginCity(IpUtils.getAddress(userLoginRo.getLastLoginIp()));
        // 记录登录Log
        UserLoginLogVo loginLog = new UserLoginLogVo();
        loginLog.setLoginData(JsonUtils.toJson(parameters));
        loginLog.setLoginIp(userLoginRo.getLastLoginIp());
        loginLog.setLoginCity(userLoginRo.getLastLoginCity());
        loginLog.setLoginStatus(LoginConstants.LoginStatus.SUCCESS);
        loginLog.setLoginTime(new Date());
        loginLog.setLoginAccount(userLoginRo.getAccount());
        try {
            // 后续增加采用策略模式
            if (LoginConstants.LoginType.PASSWORD.equals(userLoginRo.getLoginType())) {
                return this.loginByPassword(userLoginRo).getTokenDTO();
            }
            return null;
        } catch (Exception e) {
            loginLog.setLoginStatus(LoginConstants.LoginStatus.FAIL);
            e.printStackTrace();
            throw e;
        } finally {
            // 保存登陆日志
            this.saveLoginLog(loginLog);
        }

    }

    private LoginUserDTO loginByPassword(UserLoginRo ro) throws CloudException {
        // 验证码判断
        if (!captchaService.validateCaptchaImage(ro.getCaptchaId(), ro.getCaptchaCode())) {
            throw new CloudException(LoginErrorCode.CAPTCHA_CODE_VALIDATE_FAIL);
        }
        // 用户状态判断
        UserAccountVo accountVo = userService.selectUserByLogin(ro.getAccount());
        if (Objects.isNull(accountVo)) {
            throw new CloudException(LoginErrorCode.USER_PASSWORD_NOT_MATCH);
        }
        UserVo userVo = userService.selectUserByGlobalId(accountVo.getUserGlobalId());
        if (UserConstants.UserStatus.LOCK.equals(userVo.getStatus())) {
            throw new CloudException(LoginErrorCode.USER_LOCK);
        }
        if (UserConstants.UserStatus.DISABLE.equals(userVo.getStatus())) {
            throw new CloudException(LoginErrorCode.USER_DISABLE);
        }
        // 验证码密码连续错误次数
        UserLoginVo userLoginVo = this.userService.selectUserLoginByGlobalId(userVo.getUserGlobalId());
        this.checkLoginErrorCount(userLoginVo);
        // 验证用户密码
        if (!PasswordUtils.verify(ro.getAccount(), ro.getPassword(), userVo.getPassword())) {
            // 记录密码验证失败次数
            this.saveUserLoginError(ro, userLoginVo, userVo.getUserGlobalId(), 1L);
            throw new CloudException(LoginErrorCode.USER_PASSWORD_NOT_MATCH);
        }
        // 密码验证成功、清零密码连续错误次数
        this.saveUserLoginError(ro, userLoginVo, userVo.getUserGlobalId(), 0L);
        return this.login(userVo, accountVo);
    }

    /**
     * 校验连续密码错误次数
     *
     * @param userLogin
     */
    private void checkLoginErrorCount(UserLoginVo userLogin) {
        if (Objects.nonNull(userLogin) && userLogin.getLoginErrorCount() >= sysConfig.getLoginPasswordErrorCount()) {
            throw new CloudException(LoginErrorCode.LOGIN_ERROR_COUNT);
        }
    }

    /**
     * 保存登陆记录
     * @param ro
     * @param userLoginVo
     * @param userGlobalId
     * @param errorCount
     */
    private void saveUserLoginError(UserLoginRo ro,UserLoginVo userLoginVo, String userGlobalId, Long errorCount) {
        if (Objects.isNull(userLoginVo)) {
            userLoginVo = new UserLoginVo();
        }
        userLoginVo.setUserGlobalId(userGlobalId);
        if (errorCount > 0) {
            userLoginVo.setLoginErrorCount(userLoginVo.getLoginErrorCount() + errorCount);
        } else {
            userLoginVo.setLoginErrorCount(0L);
        }
        userLoginVo.setLastLoginIp(ro.getLastLoginIp());
        userLoginVo.setLastLoginCity(ro.getLastLoginCity());
        this.userService.saveUserLogin(userLoginVo);
    }

    /**
     * 用户登录封装登录参数
     *
     * @param userVo
     * @param accountVo
     * @return
     */
    private LoginUserDTO login(UserVo userVo, UserAccountVo accountVo) {
        LoginUserDTO userDTO = new LoginUserDTO();
        userDTO.setUserAccount(userVo.getUserAccount());
        userDTO.setAccount(accountVo.getAccount());
        userDTO.setUserGlobalId(userVo.getUserGlobalId());
        userDTO.setEmail(userVo.getEmail());
        userDTO.setNickName(userVo.getNickName());
        // 生成token
        userDTO.setTokenDTO(this.tokenService.generateToken(userDTO));
        return userDTO;
    }

    /**
     * 保存登陆日志
     *
     * @param loginLog
     */
    private void saveLoginLog(UserLoginLogVo loginLog) {
        String oemCode = CloudManager.getInstance().getOemCode();
        CompletableFuture.runAsync(() -> {
            try {
                CloudManager.getInstance().setOemCode(oemCode);
                loginLog.setGmtCreate(new Date());
                userService.saveUserLoginLog(loginLog);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
