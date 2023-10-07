package com.base.app.po.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class RoleModulePo extends BaseValue {
    /**
     * 角色菜单id
     */
    private Long roleModuleId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private String moduleId;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
