package com.base.app.vo.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class OemInfoVo extends BaseValue {
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
     * 删除状态
     */
    private String delStatus;

    /**
     * 租户状态
     */
    private String oemStatus;

    /**
     * 授权开始时间
     */
    private Date startDate;

    /**
     * 授权结束时间
     */
    private Date endDate;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

}
