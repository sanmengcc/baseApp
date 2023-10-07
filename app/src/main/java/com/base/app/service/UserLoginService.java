package com.base.app.service;

import com.base.app.dto.user.LoginTokenDTO;

import java.util.Map;

/**
 * 用户登录接口
 */
public interface UserLoginService {

    /**
     * 用户登录
     * @param parameters
     * @return
     */
    LoginTokenDTO userLogin(Map<String, String> parameters);
}
