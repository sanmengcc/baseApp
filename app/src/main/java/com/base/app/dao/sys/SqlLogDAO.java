package com.base.app.dao.sys;

import com.base.app.po.sys.SqlLogPo;
import com.base.core.annotation.MybatisLog;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@MybatisLog(log = false)
public interface SqlLogDAO extends BaseDAO<SqlLogPo> {

    /**
     * 删除日志
     * @param startTime
     * @param endTime
     */
    void deleteTime(String startTime, String endTime);
}
