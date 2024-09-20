package com.base.app.ro.core.oem;

import com.base.core.entity.BaseRo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class AddRo extends BaseRo {

    /**
     * 租户名称
     */
    @NotBlank(message = "租户名称不能为空")
    private String oemName;

    /**
     * 租户编码
     */
    @NotBlank(message = "租户编码不能为空")
    private String oemCode;

    /**
     * 租户状态
     */
    @NotBlank(message = "租户状态不能为空")
    private String oemStatus;

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

    /**
     * 删除状态
     */
    private String delStatus;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
