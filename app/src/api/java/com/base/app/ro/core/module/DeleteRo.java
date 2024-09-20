package com.base.app.ro.core.module;

import com.base.core.entity.BaseRo;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DeleteRo extends BaseRo {

    @NotBlank(message = "菜单ID不能为空")
    private String moduleId;
}
