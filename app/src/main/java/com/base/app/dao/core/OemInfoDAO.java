package com.base.app.dao.core;

import com.base.app.po.core.OemInfoPo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OemInfoDAO extends BaseDAO<OemInfoPo> {

    /**
     * 查询oemCode信息
     * @param oemCode
     * @return
     */
    OemInfoPo selectOemCode(String oemCode);
}
