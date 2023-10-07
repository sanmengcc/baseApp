package com.base.app.ro.oem;

import com.base.core.entity.PageRo;
import lombok.Data;

/**
 * 分页查询参数
 */
@Data
public class SearchRo extends PageRo {

    /**
     * 关键字
     */
    private String keyword;
}
