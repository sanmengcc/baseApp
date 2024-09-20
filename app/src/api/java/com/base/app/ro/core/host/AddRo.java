package com.base.app.ro.core.host;

import com.base.core.entity.BaseRo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class AddRo extends BaseRo {
    /**
     * 域名
     */
    @NotBlank(message = "域名配置不能为空")
    private String host;

    /**
     * 配置参数
     */
    @NotNull(message = "域名配置参数不能为空")
    private Map config;
}
