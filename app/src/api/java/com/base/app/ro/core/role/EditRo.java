package com.base.app.ro.core.role;

import com.base.core.entity.BaseValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class EditRo extends BaseValue {
    /**
     * 角色id
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;

    /**
     * 角色编码
     */
    @NotBlank(message = "角色编码不能为空")
    private String roleCode;

    /**
     * 角色名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色类型;1：系统默认角色 2：自定义角色
     */
    @NotBlank(message = "角色类型不能为空")
    private String roleType;
}
