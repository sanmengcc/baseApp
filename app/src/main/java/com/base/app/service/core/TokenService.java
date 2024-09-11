package com.base.app.service.core;

import com.base.app.dto.core.user.LoginTokenDTO;
import com.base.app.dto.core.user.LoginUserDTO;
import com.base.core.entity.UserInfo;

import java.util.List;

/**
 * token
 */
public interface TokenService {

    /**
     * 生成用户token
     *
     * @param userDTO
     * @return
     */
    LoginTokenDTO generateToken(LoginUserDTO userDTO);

    /**
     * 获取token中的用户信息
     * @param tokenId
     * @return
     */
    UserInfo getToken(String tokenId);

    /**
     * 退出登陆
     * @param token
     */
    void logout(String token);

    /**
     * 获取用户权限
     * @param token
     * @return
     */
    List<String> getPermissionURL(String token);
}
