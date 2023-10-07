package com.base.core.error;

/**
 * 错误码接口
 */
public interface IErrorCode {

    /**
     * 成功返回码
     */
    public static String SUCCESS = "200";

    /**
     * 成功描述
     */
    public static String SUCCESS_DESC = "success";

    /**
     * 系统异常返回码
     */
    public static String SYSTEM_ERROR = "500";

    /**
     * 获取错误码描述
     */
    public String getDesc();

    /**
     * 获取错误码描述（等同于getDesc()）
     */
    public String toString();

    /**
     * 获取错误码编码
     */
    public String name();
}
