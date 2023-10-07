package com.base.app.vo.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class SystemModuleVo extends BaseValue {

    private static final long serialVersionUID = 1L;

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
     * 服务端接口地址
     */
    private String serverApi;

    /**
     * 权限编码
     */
    private String authCode;

    /**
     * 是否还有子级;1:否 2:是
     */
    private String hasChild;

    /**
     * 功能类型;功能类别；1：目录；2：功能链接；3：页面；4：按钮
     */
    private String type;

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
     * 启用状态;1：已启用；2：未启用/禁用
     */
    private String enableStatus;

    /**
     * 显示状态;1：显示；2：隐藏
     */
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
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

}

