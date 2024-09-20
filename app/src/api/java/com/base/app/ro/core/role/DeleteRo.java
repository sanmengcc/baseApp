package com.base.app.ro.core.role;

import com.base.core.entity.BaseValue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class DeleteRo extends BaseValue {
    /**
     * 角色id
     */
    @NotNull(message = "角色ID不能为空")
    private Long roleId;
}
