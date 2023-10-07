package com.base.core.entity;

import lombok.Data;

/**
 * 分页参数
 */
@Data
public class PageRo extends BaseValue{

    /**
     * 当前页
     */
    private Long currentPage;

    /**
     * 分页查询的条数
     */
    private Long pageSize;
}
