package com.base.core.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 分页参数
 */
@Data
public class PageRo extends BaseValue{

    /**
     * 当前页
     */
    @NotNull(message = "分页参数currentPage不能为空")
    private Long currentPage;

    /**
     * 分页查询的条数
     */
    @NotNull(message = "分页参数pageSize不能为空")
    private Long pageSize;
}
