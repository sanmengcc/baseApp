package com.base.app.dto.log;

import com.base.app.constants.FieldConstants;
import com.base.core.annotation.DictAnnotation;
import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class RequestLogDTO extends BaseValue {

    /**
     * 操作账号
     */
    private String account;

    /**
     * 操作ip
     */
    private String ip;

    /**
     * 操作方法名称
     */
    private String methodName;

    /**
     * 执行结果
     */
    @DictAnnotation(name = FieldConstants.Common.FLAG)
    private String result;

    /**
     * 执行时间
     */
    private Long useTime;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
}
