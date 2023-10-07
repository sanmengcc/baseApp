package com.base.app.dto.log;

import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SqlLogDTO extends BaseValue {

    /**
     * 日志id
     */
    private Long logId;

    /**
     * mapper路径
     */
    private String mapper;

    /**
     * sql语句
     */
    private String sql;

    /**
     * 耗时
     */
    private Long useTime;

    /**
     * 创建时间
     */
    @JsonFormat(timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
}
