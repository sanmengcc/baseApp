package com.base.app.ro.role;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class EditRo extends BaseValue {
    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型;1：系统默认角色 2：自定义角色
     */
    private String roleType;
}
