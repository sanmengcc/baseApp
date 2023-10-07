package com.base.app.ro.host;

import com.base.core.entity.PageRo;
import lombok.Data;

@Data
public class SearchRo extends PageRo {

    /**
     * 域名信息
     */
    private String host;
}
