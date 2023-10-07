package com.base.app.po.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class HostConfigPo extends BaseValue {
    /**
     * 配置id
     */
    private Long configId;

    /**
     * 域名
     */
    private String host;

    /**
     * json配置
     */
    private String configJson;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
