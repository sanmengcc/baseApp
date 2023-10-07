package com.base.app.ro.staff;

import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
public class AddRo extends BaseValue {


    /**
     * 员工类型;001:系统管理员；s02；普通员工
     */
    private String adminType;

    /**
     * 用户名
     */
    private String account;

    /**
     * 入职日期
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;

    /**
     * 离职日期
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date leftDate;

    /**
     * 离职状态;1：在职 2：离职
     */
    private String status;

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
