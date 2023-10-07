package com.base.app.ro.role;

import com.base.core.entity.BaseRo;
import lombok.Data;

@Data
public class AllotRoleRo extends BaseRo {

    /**
     * 员工ID
     */
    private String staffId;

    /**
     * 角色ID集合
     */
    private Long[] roleIdList;
}
