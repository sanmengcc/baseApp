package com.base.app.ro.role;

import com.base.core.entity.PageRo;
import lombok.Data;


@Data
public class SearchRo extends PageRo {

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
