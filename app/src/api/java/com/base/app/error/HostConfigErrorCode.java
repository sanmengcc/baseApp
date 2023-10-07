package com.base.app.error;


import com.base.core.error.IErrorCode;

/**
 * 域名配置相关错误
 */
public enum HostConfigErrorCode implements IErrorCode {

    HOST_EXIST("域名配置已存在！"),
    ;

    private final String desc;

    HostConfigErrorCode(String desc) {
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