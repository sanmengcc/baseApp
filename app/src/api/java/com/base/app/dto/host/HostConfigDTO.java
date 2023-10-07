package com.base.app.dto.host;

import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class HostConfigDTO extends BaseValue {
    /**
     * 配置id
     */
    private Long configId;

    /**
     * 域名
     */
    private String host;

    /**
     * 系统名称
     */
    private String sysName;

    /**
     * 租户编码
     */
    private String oemCode;

    /**
     * json配置
     */
    private String configJson;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
}
