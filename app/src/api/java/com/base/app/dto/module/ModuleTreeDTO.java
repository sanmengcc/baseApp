package com.base.app.dto.module;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.List;

@Data
public class ModuleTreeDTO extends BaseValue {
    /**
     * 菜单id
     */
    private String moduleId;

    /**
     * 菜单父id
     */
    private String parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 是否还有子级;1:否 2:是
     */
    private String hasChild;

    /**
     * 是否还有子级
     */
    private Boolean isLeaf = false;

    /**
     * 子级数据
     */
    private List<ModuleTreeDTO> children;

}
