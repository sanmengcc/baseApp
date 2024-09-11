package com.base.app.ro.core.staff;

import com.base.core.entity.PageRo;
import lombok.Data;

@Data
public class SearchRo extends PageRo {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 在职状态
     */
    private String status;

}
