package com.base.app.service;

import com.base.app.dto.log.SqlLogDTO;
import com.base.app.ro.log.SQLLogPageRo;
import com.base.core.entity.Page;

public interface SQLLogService {

    /**
     * 分页查询日志
     * @param ro
     * @return
     */
    Page<SqlLogDTO> searchPage(SQLLogPageRo ro);

    /**
     * 查询日志详情
     * @param sqlId
     * @return
     */
    SqlLogDTO selectById(Long sqlId);

    /**
     * 删除日志
     * @param key
     */
    void delete(String key);
}
