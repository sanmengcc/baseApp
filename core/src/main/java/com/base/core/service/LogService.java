package com.base.core.service;

import com.base.core.entity.dto.RequestLog;
import com.base.core.entity.dto.SqlLogDTO;

/**
 * 接口日志
 */
public interface LogService {

    /**
     * 保存接口日志
     * @param requestLog
     */
    void saveLog(RequestLog requestLog);

    /**
     * 保存SQL执行日志
     * @param sqlLog
     */
    void saveSQL(SqlLogDTO sqlLog);
}
