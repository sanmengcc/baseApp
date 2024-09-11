package com.base.app.service.core;

import com.base.app.dto.core.log.SqlLogDTO;
import com.base.app.ro.core.log.SQLLogPageRo;
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
