package com.base.app.vo.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class StaffVo extends BaseValue {

    /**
     * 员工id
     */
    private String staffId;

    /**
     * 用户全局id
     */
    private String userGlobalId;

    /**
     * 用户名
     */
    private String account;

    /**
     * 员工类型;001:系统管理员；s02；普通员工
     */
    private String adminType;

    /**
     * 入职日期
     */
    private Date entryDate;

    /**
     * 离职日期
     */
    private Date leftDate;

    /**
     * 上次登陆时间
     */
    private Date lastLoginTime;

    /**
     * 离职状态;1：正常 2：禁止 3：已离职
     */
    private String status;

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
