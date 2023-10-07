package com.base.app.vo.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class StaffRoleVo extends BaseValue {
    /**
     * 员工角色id
     */
    private Long staffRoleId;

    /**
     * 员工id
     */
    private String staffId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
