package com.base.core.exception;

import java.text.MessageFormat;

/**
 * 全局自定义异常
 */
public class CloudException extends RuntimeException {
    private static final long serialVersionUID = 6961926234532027069L;
    private String code;

    public CloudException(String message) {
        super(message);
    }

    public CloudException(Throwable cause) {
        super(cause);
    }

    public CloudException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudException() {
    }

    public CloudException(String code, String msg) {
        this(msg);
        this.code = code;
    }

    public CloudException(Enum<?> en) {
        this(en.name(), en.toString());
    }

    public CloudException(Enum<?> en, Object... args) {
        this(en.name(), MessageFormat.format(en.toString(), args));
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
