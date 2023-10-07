package com.base.app.error;


import com.base.core.error.IErrorCode;

/**
 * 员工相关错误
 */
public enum StaffErrorCode implements IErrorCode {

    STAFF_IS_NOT_EXIST("员工账号不存在！"),
    SUPER_ADMIN_NOT_DELETE("超级管理员不允许删除！"),
    ;

    private final String desc;

    StaffErrorCode(String desc) {
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