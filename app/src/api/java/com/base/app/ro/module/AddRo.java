package com.base.app.ro.module;

import com.base.core.entity.BaseRo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 分页查询菜单参数
 */
@Data
public class AddRo extends BaseRo {

    /**
     * 菜单父id
     */
    private String parentId;

    @Length(max = 20, message = "路由名称不能超过20个字符")
    private String routerName;

    /**
     * 路由设置
     */
    @Length(max = 100, message = "路由设置不能超过100个字符")
    private String routerPath;

    /**
     * 系统id
     */
    @NotNull(message = "系统ID不能为空")
    private Long systemId;

    /**
     * 菜单名称
     */
    @NotNull(message = "菜单名称不能为空")
    @Length(max = 20, message = "菜单名称不能超过20个字符")
    private String name;

    /**
     * 权限编码
     */
    private String authCode;

    /**
     * 菜单编码
     */
    private String code;

    /**
     * 功能类型;功能类别；1：目录；2：功能链接；3：页面；4：按钮
     */
    @NotNull(message = "功能类型不能为空")
    @Length(max = 1, message = "功能类型不能超过1个字符")
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
     * 启用状态;1：已启用；2：未启用/禁用
     */
    @NotNull(message = "启用状态不能为空")
    @Length(max = 1, message = "启用状态不能超过1个字符")
    private String enableStatus;

    /**
     * 显示状态;1：显示；2：隐藏
     */
    @NotNull(message = "显示状态不能为空")
    @Length(max = 1, message = "显示状态不能超过1个字符")
    private String hidden;

    /**
     * 服务端接口地址
     */
    private String serverApi;

    /**
     * 配置参数
     */
    private String configJson;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空")
    private Integer seq;
}
