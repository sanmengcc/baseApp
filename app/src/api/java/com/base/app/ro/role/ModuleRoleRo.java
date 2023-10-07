package com.base.app.ro.role;

import com.base.core.entity.BaseRo;
import lombok.Data;

@Data
public class ModuleRoleRo extends BaseRo {

    /**
     * 菜单ID集合
     */
    private String[] moduleIdList;

    /**
     * 角色ID
     */
    private Long roleId;
}
