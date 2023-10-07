package com.base.app.ro.user;

import com.base.core.entity.PageRo;
import lombok.Data;

@Data
public class SearchRo extends PageRo {

    /**
     * 关键字
     */

    private String keyword;

    /**
     * 用户状态
     */
    private String status;
}
