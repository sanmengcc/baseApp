package com.base.app.ro.login;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class UserLoginRo extends BaseValue {

    /**
     * 登录账号
     */
    private String account;

    /**
     * 验证码
     */
    private String captchaCode;

    /**
     * 验证码ID
     */
    private String captchaId;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 登录类型
     */
    private String loginType;

    /**
     * 上一次登录ip
     */
    private String lastLoginIp;

    /**
     * 上一次登录城市
     */
    private String lastLoginCity;
}
