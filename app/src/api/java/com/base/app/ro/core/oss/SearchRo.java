package com.base.app.ro.core.oss;

import com.base.core.entity.PageRo;
import lombok.Data;

@Data
public class SearchRo extends PageRo {

    /**
     * 关键字
     */
    private String keyword;
}
