package com.base.core.entity.dto;

import com.base.core.entity.BaseValue;
import lombok.Data;

@Data
public class RequestLog extends BaseValue {

    /**
     * 操作账号
     */
    private String account;

    /**
     * 操作IP
     */
    private String ip;

    /**
     * 操作方法
     */
    private String method;

    /**
     * 操作方法名称
     */
    private String methodName;

    /**
     * 操作结果
     */
    private String result;

    /**
     * 接口耗时
     */
    private Long useTime;

    /**
     * 请求参数
     */
    private String requestData;

    /**
     * 响应参数
     */
    private String responseData;
}
