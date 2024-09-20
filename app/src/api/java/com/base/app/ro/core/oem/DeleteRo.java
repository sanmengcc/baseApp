package com.base.app.ro.core.oem;

import com.base.core.entity.BaseRo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class DeleteRo extends BaseRo {
    /**
     * 租户id
     */
    @NotNull(message = "租户ID不能为空")
    private Long oemId;
}
