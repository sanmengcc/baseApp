package com.base.app.ro.host;

import com.base.core.entity.BaseRo;
import lombok.Data;

import java.util.Map;

@Data
public class EditRo extends BaseRo {

    /**
     * 配置ID
     */
    private Long configId;

    /**
     * 域名
     */
    private String host;

    /**
     * 配置参数
     */
    private Map config;
}
