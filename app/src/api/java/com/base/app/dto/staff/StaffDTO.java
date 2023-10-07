package com.base.app.dto.staff;

import com.base.app.constants.FieldConstants;
import com.base.core.annotation.DictAnnotation;
import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StaffDTO extends BaseValue {

    /**
     * 员工id
     */
    private String staffId;

    /**
     * 用户名
     */
    private String account;

    /**
     * 用户全局id
     */
    private String userGlobalId;

    /**
     * 员工类型;001:系统管理员；s02；普通员工
     */
    @DictAnnotation(name = FieldConstants.Staff.ADMIN_TYPE)
    private String adminType;

    /**
     * 上次登陆时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;

    /**
     * 入职日期
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;

    /**
     * 离职日期
     */
    private Date leftDate;

    /**
     * 离职状态;1：在职 2：离职
     */
    @DictAnnotation(name = FieldConstants.Staff.WORK_STATUS)
    private String status;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    /**
     * 员工档案id
     */
    private String archiveId;

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
}
