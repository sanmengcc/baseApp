package com.base.app.dao.sys;

import com.base.app.po.sys.HostConfigPo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HostConfigDAO extends BaseDAO<HostConfigPo> {

    /**
     * 按域名查询
     * @param host
     * @return
     */
    HostConfigPo selectHost(String host);
}
