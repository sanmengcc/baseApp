package com.base.app.ro.core.log;

import com.base.core.entity.PageRo;
import lombok.Data;

@Data
public class SQLLogPageRo extends PageRo {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 开始时间
     */
    private String startTime;
}
