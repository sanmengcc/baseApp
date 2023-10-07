package com.base.app.service.impl;

import com.base.app.constants.UserConstants;
import com.base.app.dao.sys.UserAccountDAO;
import com.base.app.dao.sys.UserDAO;
import com.base.app.dao.sys.UserLoginDAO;
import com.base.app.dao.sys.UserLoginLogDAO;
import com.base.app.dto.log.LoginLogDTO;
import com.base.app.dto.user.UserAccountDTO;
import com.base.app.dto.user.UserDTO;
import com.base.app.error.UserErrorCode;
import com.base.app.po.sys.UserAccountPo;
import com.base.app.po.sys.UserLoginLogPo;
import com.base.app.po.sys.UserLoginPo;
import com.base.app.po.sys.UserPo;
import com.base.app.ro.log.LoginLogPageRo;
import com.base.app.ro.user.SearchRo;
import com.base.app.ro.user.UserRegisterRo;
import com.base.app.service.UserService;
import com.base.app.util.PasswordUtils;
import com.base.app.vo.sys.UserAccountVo;
import com.base.app.vo.sys.UserLoginLogVo;
import com.base.app.vo.sys.UserLoginVo;
import com.base.app.vo.sys.UserVo;
import com.base.core.constant.CommonConstants;
import com.base.core.entity.Page;
import com.base.core.entity.PageRo;
import com.base.core.entity.Paging;
import com.base.core.exception.CloudException;
import com.base.util.IdUtils;
import com.base.util.JsonUtils;
import com.base.util.ValidateHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserAccountDAO userAccountDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private UserLoginLogDAO userLoginLogDAO;
    @Resource
    private UserLoginDAO userLoginDAO;

    @Override
    public UserAccountVo selectUserByLogin(String account) {
        return JsonUtils.createBean(userAccountDAO.selectByAccount(account), UserAccountVo.class);
    }

    @Override
    public UserVo selectUserById(Long userId) {
        return JsonUtils.createBean(userDAO.queryById(userId), UserVo.class);
    }

    @Override
    public UserVo selectUserByGlobalId(String userGlobalId) {
        return JsonUtils.createBean(userDAO.selectByGlobalId(userGlobalId), UserVo.class);
    }

    @Override
    public void saveUserLoginLog(UserLoginLogVo loginLog) {
        UserLoginLogPo logPo = JsonUtils.createBean(loginLog, UserLoginLogPo.class);
        logPo.setGmtCreate(new Date());
        userLoginLogDAO.add(logPo);
    }

    @Override
    public UserLoginVo selectUserLoginByGlobalId(String userGlobalId) {
        UserLoginPo userLoginPo = userLoginDAO.queryById(userGlobalId);
        return JsonUtils.createBean(userLoginPo, UserLoginVo.class);
    }

    @Override
    public void saveUserLogin(UserLoginVo userLogin) {
        UserLoginPo userLoginPo = JsonUtils.createBean(userLogin, UserLoginPo.class);
        if (Objects.nonNull(userLoginPo.getLoginId())) {
            userLogin.setGmtModified(new Date());
            this.userLoginDAO.update(userLoginPo);
        } else {
            userLoginPo.setGmtCreate(new Date());
            this.userLoginDAO.add(userLoginPo);
        }
    }

    @Override
    public Page<LoginLogDTO> selectLoginLogPage(LoginLogPageRo ro, PageRo pageRo) {
        // 设置分页参数
        Paging paging = Paging.build(pageRo);
        paging.extra(JsonUtils.bean2Map(ro));
        // 总数查询
        Long count = userLoginLogDAO.selectCount(paging);
        List<LoginLogDTO> dataList = new ArrayList<>();
        if (count > 0) {
            // 设置偏移量
            paging.setRowCount(count);
            // 列表查询
            dataList = JsonUtils.createList(userLoginLogDAO.selectPage(paging), LoginLogDTO.class);
        }
        return Page.build(paging).total(count).data(dataList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String register(UserRegisterRo registerRo) {
        // 校验账号信息
        this.checkAccount(registerRo.getAccounts(), null);
        // 生成用户密码
        String md5 = PasswordUtils.MD5(registerRo.getUserAccount());
        String password = PasswordUtils.generate(registerRo.getUserAccount(), md5);
        // 创建用户数据
        UserPo userPo = JsonUtils.createBean(registerRo, UserPo.class);
        userPo.setPassword(password);
        // 生成用户全局ID
        String userGlobalId = IdUtils.generateId(UserConstants.USER_ID_PREFIX);
        userPo.setUserGlobalId(userGlobalId);
        userPo.setDelStatus(CommonConstants.Del.NOT_DEL);
        userPo.setStatus(UserConstants.UserStatus.NORMAL);
        this.saveUser(userPo);
        // 保存用户账号信息
        this.saveUserAccount(userGlobalId, registerRo.getAccounts());
        return userGlobalId;
    }

    @Override
    public void updateRegister(UserRegisterRo registerRo) {
        // 校验账号信息
        this.checkAccount(registerRo.getAccounts(), registerRo.getUserGlobalId());
        UserPo userPo = JsonUtils.createBean(registerRo, UserPo.class);
        // 修改用户信息
        this.saveUser(userPo);
        // 保存用户账号信息
        this.saveUserAccount(registerRo.getUserGlobalId(), registerRo.getAccounts());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updatePasswordByAdmin(String userGlobalId, String newPassword) {
        UserPo userPo = this.userDAO.selectByGlobalId(userGlobalId);
        String password = PasswordUtils.generate(userPo.getUserAccount(), newPassword);
        this.userDAO.changePassword(userGlobalId, password);

        //处理登陆数据
        UserLoginVo userLoginVo = Optional.ofNullable(this.selectUserLoginByGlobalId(userGlobalId))
                .orElse(new UserLoginVo());
        userLoginVo.setUserGlobalId(userGlobalId);
        userLoginVo.setLoginErrorCount(0L);
        userLoginVo.setChangeDate(new Date());
        this.saveUserLogin(userLoginVo);
    }

    @Override
    public Page<UserDTO> searchPage(SearchRo ro) {
        // 设置分页参数
        Paging paging = Paging.build(ro);
        // 总数查询
        Long count = userDAO.selectCount(paging);
        // 设置偏移量
        paging.setRowCount(count);
        // 分页查询
        return paging.search(userDAO::selectPage, UserDTO.class);
    }

    /**
     * 保存用户账号信息
     *
     * @param userGlobalId
     * @param userAccountDTOS
     */
    private void saveUserAccount(String userGlobalId, List<UserAccountDTO> userAccountDTOS) {
        this.userAccountDAO.deleteUserGlobalId(userGlobalId);
        // 新增
        List<UserAccountPo> accountList = JsonUtils.createList(userAccountDTOS, UserAccountPo.class);
        accountList.forEach(account -> {
            account.setStatus(UserConstants.AccountStatus.NORMAL);
            account.setUserGlobalId(userGlobalId);
            account.setGmtCreate(new Date());
        });
        this.userAccountDAO.addList(accountList);
    }

    /**
     * 保存用户数据
     *
     * @param userPo
     * @return
     */
    private Long saveUser(UserPo userPo) {
        if (ValidateHelper.isNotEmptyString(userPo.getUserGlobalId())) {
            userPo.setGmtModified(new Date());
            this.userDAO.update(userPo);
            return userPo.getUserId();
        }
        userPo.setGmtCreate(new Date());
        this.userDAO.add(userPo);
        return userPo.getUserId();
    }

    /**
     * 校验登陆账号是否存在或者绑定
     *
     * @param accountDTOS
     */
    private void checkAccount(List<UserAccountDTO> accountDTOS, String userGlobalId) {
        if (ValidateHelper.isEmptyString(userGlobalId)) {
            // 处理新增
            accountDTOS.forEach(account -> {
                UserAccountPo userAccountPo = this.userAccountDAO.selectByAccount(account.getAccount());
                if (Objects.nonNull(userAccountPo)) {
                    if (UserConstants.AccountType.USER.equals(account.getAccountType())) {
                        throw new CloudException(UserErrorCode.USER_ACCOUNT_USER_BIND);
                    }
                    if (UserConstants.AccountType.EMAIL.equals(account.getAccountType())) {
                        throw new CloudException(UserErrorCode.USER_ACCOUNT_EMAIL_BIND);
                    }
                    if (UserConstants.AccountType.MOBILE.equals(account.getAccountType())) {
                        throw new CloudException(UserErrorCode.USER_ACCOUNT_MOBILE_BIND);
                    }
                }
            });
        } else {
            // 处理修改
            accountDTOS.forEach(account -> {
                UserAccountPo userAccountPo = this.userAccountDAO.selectByAccount(account.getAccount());
                if (Objects.nonNull(userAccountPo)) {
                    if (!userAccountPo.getUserGlobalId().equals(userGlobalId)) {
                        if (UserConstants.AccountType.USER.equals(account.getAccountType())) {
                            throw new CloudException(UserErrorCode.USER_ACCOUNT_USER_BIND);
                        }
                        if (UserConstants.AccountType.EMAIL.equals(account.getAccountType())) {
                            throw new CloudException(UserErrorCode.USER_ACCOUNT_EMAIL_BIND);
                        }
                        if (UserConstants.AccountType.MOBILE.equals(account.getAccountType())) {
                            throw new CloudException(UserErrorCode.USER_ACCOUNT_MOBILE_BIND);
                        }
                    }
                    account.setAccountId(userAccountPo.getAccountId());
                }
            });
        }
    }
}
