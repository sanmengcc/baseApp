package com.base.app.service;

import com.base.app.dto.log.RequestLogDTO;
import com.base.app.ro.log.ApiLogPageRo;
import com.base.core.entity.Page;

public interface RequestLogService {

    /**
     * 分页查询接口日志
     * @param ro
     * @return
     */
    Page<RequestLogDTO> searchPage(ApiLogPageRo ro);

    /**
     * 按时间删除接口日志
     * @param timeKey
     */
    void delete(String timeKey);
}
