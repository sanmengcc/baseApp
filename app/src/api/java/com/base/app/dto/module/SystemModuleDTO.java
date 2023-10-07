package com.base.app.dto.module;

import com.base.app.constants.FieldConstants;
import com.base.app.enums.ModuleEnum;
import com.base.core.annotation.DictAnnotation;
import com.base.core.entity.BaseValue;
import com.base.core.enums.CommonEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SystemModuleDTO extends BaseValue {
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
     * 路由名称
     */
    private String routerName;

    /**
     * 权限编码
     */
    private String authCode;

    /**
     * 功能类型;功能类别；1：目录；2：功能链接；3：页面；4：按钮
     */
    @DictAnnotation(name = FieldConstants.Module.TYPE)
    private String type;

    /**
     * 服务端接口地址
     */
    private String serverApi;

    /**
     * 跳转配置
     */
    private String jumpAction;

    /**
     * icon图标
     */
    private String icon;

    /**
     * 删除状态;1：未删除；2：已删除
     */
    private String delStatus;

    /**
     * 是否还有子级;1:否 2:是
     */
    private boolean hasChildren;

    /**
     * 是否还有子级;1:否 2:是
     */
    @DictAnnotation(name = FieldConstants.Common.HAS_CHILD)
    private String hasChild;

    /**
     * 启用状态;1：已启用；2：未启用/禁用
     */
    @DictAnnotation(name = FieldConstants.Common.ENABLE_STATUS)
    private String enableStatus;

    /**
     * 显示状态;1：显示；2：隐藏
     */
    @DictAnnotation(name = FieldConstants.Common.HIDDEN_STATUS)
    private String hidden;

    /**
     * 配置参数
     */
    private String configJson;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
