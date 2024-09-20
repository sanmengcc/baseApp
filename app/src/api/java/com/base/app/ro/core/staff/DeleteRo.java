package com.base.app.ro.core.staff;

import com.base.core.entity.BaseValue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteRo extends BaseValue {

    /**
     * 员工ID
     */
    @NotBlank(message = "员工ID不能为空")
    private String staffId;

    /**
     * 用户全局ID
     */
    @NotBlank(message = "用户全局ID不能为空")
    private String userGlobalId;
}
