package com.base.core.entity;

import lombok.Data;

import java.util.List;

/**
 * 树形结构基类
 */
@Data
public class BaseTree extends BaseValue{

    /**
     * 节点ID
     */
    private Object id;

    /**
     * 父节点ID
     */
    private Object parentId;

    /**
     * 显示文本
     */
    private String label;

    /**
     * 排序字段
     */
    private Integer seq;

    /**
     * 是否禁用
     */
    private boolean disabled = false;

    /**
     * 子节点数据
     */
    private List<BaseTree> children;
}
