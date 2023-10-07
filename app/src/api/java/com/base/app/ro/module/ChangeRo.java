package com.base.app.ro.module;

import com.base.core.entity.BaseRo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 分页查询菜单参数
 */
@Data
public class ChangeRo extends BaseRo {

    /**
     * 菜单id
     */
    @NotNull(message = "菜单ID不能为空")
    @Length(max = 50, message = "菜单ID不能超过50个字符")
    private String moduleId;

    /**
     * 启用状态;1：已启用；2：未启用/禁用
     */
    private String enableStatus;

    /**
     * 显示状态;1：显示；2：隐藏
     */
    private String hidden;

    /**
     * 排序
     */
    private Integer seq;
}
