package com.base.app.error;


import com.base.core.error.IErrorCode;

/**
 *租户编码相关错误
 */
public enum OemInfoErrorCode implements IErrorCode {

    OEM_CODE_EXIST("租户编码已存在！"),
    ;

    private final String desc;

    OemInfoErrorCode(String desc) {
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