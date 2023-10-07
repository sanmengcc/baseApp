package com.base.core.entity.dto;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class SqlLogDTO extends BaseValue {

    /**
     * SQL
     */
    private String sql;

    /**
     * SQL ID
     */
    private String mapper;

    /**
     * 耗时
     */
    private Long useTime;

    /**
     * 执行时间
     */
    private Date gmtCreate;
}
