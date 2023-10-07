package com.base.app.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class VueRouterModuleVo extends BaseModule{

    /**
     * ID
     */
    @JsonIgnore
    private Object id;

    /**
     * 父ID
     */
    @JsonIgnore
    private Object parentId;

    /**
     * 排序
     */
    @JsonIgnore
    private Integer seq;

    /**
     * 路径
     */
    private String path;

    /**
     * 路由
     */
    private String component;

    /**
     * 显示隐藏
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean hidden;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 元数据
     */
    private Map<String, String> meta;

    /**
     * 子路由
     */
    private Boolean alwaysShow;


    /**
     * 子数据
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<VueRouterModuleVo> children;
}
