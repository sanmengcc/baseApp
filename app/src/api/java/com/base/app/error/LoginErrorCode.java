package com.base.app.error;


import com.base.core.error.IErrorCode;

/**
 * 登陆相关错误
 */
public enum LoginErrorCode implements IErrorCode {

    CAPTCHA_CODE_VALIDATE_FAIL("验证码错误！"),

    USER_LOCK("用户已锁定！"),

    USER_DISABLE("用户已封禁！"),
    LOGIN_ERROR_COUNT("密码连续错误达到上限！"),

    USER_PASSWORD_NOT_MATCH("用户账号密码不匹配或不存在！"),
    ;

    private final String desc;

    LoginErrorCode(String desc) {
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return null;
    }

    @Override
    public String toString() {
        return desc;
    }
}