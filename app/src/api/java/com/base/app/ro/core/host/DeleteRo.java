package com.base.app.ro.core.host;

import com.base.core.entity.BaseRo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteRo extends BaseRo {

    @NotNull(message = "配置ID不能为空")
    private Long configId;
}
