package com.base.app.service.core;

import com.base.app.dto.core.log.LoginLogDTO;
import com.base.app.dto.core.user.UserDTO;
import com.base.app.ro.core.log.LoginLogPageRo;
import com.base.app.ro.core.user.SearchRo;
import com.base.app.ro.core.user.UserRegisterRo;
import com.base.app.vo.core.UserAccountVo;
import com.base.app.vo.core.UserLoginLogVo;
import com.base.app.vo.core.UserLoginVo;
import com.base.app.vo.core.UserVo;
import com.base.core.entity.Page;
import com.base.core.entity.PageRo;

/**
 * 用户相关接口
 */
public interface UserService {

    /**
     * 根据账号查询用户信息
     * @param account
     * @return
     */
    UserAccountVo selectUserByLogin(String account);

    /**
     * 根据用户ID查询用户信息
     * @param userId
     * @return
     */
    UserVo selectUserById(Long userId);

    /**
     * 根据用户全局ID查询用户信息
     * @param userGlobalId
     * @return
     */
    UserVo selectUserByGlobalId(String userGlobalId);

    /**
     * 保存登录日志
     * @param loginLog
     */
    void saveUserLoginLog(UserLoginLogVo loginLog);

    /**
     * 根据用户全局ID查询用户登陆记录
     * @param userGlobalId
     * @return
     */
    UserLoginVo selectUserLoginByGlobalId(String userGlobalId);

    /**
     * 保存用户记录
     * @param userLogin
     */
    void saveUserLogin(UserLoginVo userLogin);

    /**
     * 查询登陆日志列表
     * @param ro
     * @param pageRo
     * @return
     */
    Page<LoginLogDTO> selectLoginLogPage(LoginLogPageRo ro, PageRo pageRo);

    /**
     * 用户注册
     * @param registerRo
     * @return
     */
    String register(UserRegisterRo registerRo);

    /**
     * 修改用户注册信息
     * @param registerRo
     */
    void updateRegister(UserRegisterRo registerRo);

    /**
     * 强制修改密码
     * @param userGlobalId
     * @param newPassword
     */
    void updatePasswordByAdmin(String userGlobalId, String newPassword);

    /**
     * 分页查询用户列表
     * @param ro
     * @return
     */
    Page<UserDTO> searchPage(SearchRo ro);
}
