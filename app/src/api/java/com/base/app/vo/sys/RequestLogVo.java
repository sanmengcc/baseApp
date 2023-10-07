package com.base.app.vo.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class RequestLogVo extends BaseValue {
    /**
     * 日志id
     */
    private Long logId;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 年月
     */
    private Integer yearOfMonth;

    /**
     * 操作账号
     */
    private String account;

    /**
     * 操作ip
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
     * 执行结果
     */
    private String result;

    /**
     * 执行时间
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

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
