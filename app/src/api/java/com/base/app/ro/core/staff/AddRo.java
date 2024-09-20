package com.base.app.ro.core.staff;

import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
public class AddRo extends BaseValue {


    /**
     * 员工类型;001:系统管理员；s02；普通员工
     */
    @NotBlank(message = "员工类型不能为空")
    private String adminType;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String account;

    /**
     * 入职日期
     */
    @NotNull(message = "入职日期不能为空")
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
    @NotBlank(message = "离职状态不能为空")
    private String status;

    /**
     * 员工姓名
     */
    @NotBlank(message = "员工姓名不能为空")
    private String staffName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
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
