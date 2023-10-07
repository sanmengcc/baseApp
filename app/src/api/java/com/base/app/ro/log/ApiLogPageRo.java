package com.base.app.ro.log;

import com.base.core.entity.PageRo;
import lombok.Data;

@Data
public class ApiLogPageRo extends PageRo {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 请求状态
     */
    private String result;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
