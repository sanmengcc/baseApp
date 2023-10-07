package com.base.app.ro.module;

import com.base.core.constant.CommonConstants;
import com.base.core.entity.PageRo;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 分页查询菜单参数
 */
@Data
public class SearchRo extends PageRo {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 父菜单ID
     */
    private String parentId;
}
