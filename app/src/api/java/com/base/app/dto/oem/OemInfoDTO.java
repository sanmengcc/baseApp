package com.base.app.dto.oem;

import com.base.app.constants.FieldConstants;
import com.base.core.annotation.DictAnnotation;
import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OemInfoDTO extends BaseValue {
    /**
     * 租户id
     */
    private Long oemId;

    /**
     * 租户名称
     */
    private String oemName;

    /**
     * 租户编码
     */
    private String oemCode;

    /**
     * 租户联系电话
     */
    private String oemMobile;

    /**
     * 租户联系地址
     */
    private String oemAddr;

    /**
     * 租户简介
     */
    private String oemDesc;

    /**
     * 租户状态
     */
    @DictAnnotation(name = FieldConstants.Oem.OEM_STATUS)
    private String oemStatus;

    /**
     * 授权开始时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * 授权结束时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
}
