package com.base.app.ro.oem;

import com.base.core.entity.BaseRo;
import lombok.Data;

import java.util.Date;

@Data
public class EditRo extends BaseRo {
    /**
     * 租户id
     */
    private Long oemId;

    /**
     * 租户名称
     */
    private String oemName;

    /**
     * 租户状态
     */
    private String oemStatus;

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
     * 授权开始时间
     */
    private Date startDate;

    /**
     * 授权结束时间
     */
    private Date endDate;
}
