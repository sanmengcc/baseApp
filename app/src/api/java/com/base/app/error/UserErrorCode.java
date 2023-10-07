package com.base.app.error;


import com.base.core.error.IErrorCode;

/**
 * 用户相关错误
 */
public enum UserErrorCode implements IErrorCode {

    USER_ACCOUNT_IS_NOT_EXIST("用户账号不存在！"),
    USER_ACCOUNT_USER_BIND("用户名已占用！"),
    USER_ACCOUNT_MOBILE_BIND("手机号码已占用！"),
    USER_ACCOUNT_EMAIL_BIND("电子邮箱已占用！"),
    ;

    private final String desc;

    UserErrorCode(String desc) {
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