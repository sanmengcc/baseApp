package com.base.app.po.sys;

import com.base.core.entity.BaseValue;
import lombok.Data;

import java.util.Date;

@Data
public class SqlLogPo extends BaseValue {
    /**
     * 日志id
     */
    private Long logId;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 年月
     */
    private Integer yearOfMonth;

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
    private Date gmtCreate;
}
