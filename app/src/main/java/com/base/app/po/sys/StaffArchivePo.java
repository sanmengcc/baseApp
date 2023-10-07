package com.base.app.po.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class StaffArchivePo extends BaseValue {
    /**
     * 员工档案id
     */
    private String archiveId;

    /**
     * 员工id
     */
    private String staffId;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 头像url
     */
    private String avatarUrl;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
