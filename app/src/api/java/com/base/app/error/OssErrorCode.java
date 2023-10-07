package com.base.app.error;

import com.base.core.error.IErrorCode;

/**
 * OSS相关错误
 */
public enum OssErrorCode implements IErrorCode {

    SYSTEM_ERROR("文件服务异常！"),

    CHANNEL_NOT_EXIST("OSS服务通道不存在！"),
    ;

    private final String desc;

    OssErrorCode(String desc) {
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