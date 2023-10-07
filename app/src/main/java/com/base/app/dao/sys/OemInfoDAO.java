package com.base.app.dao.sys;

import com.base.app.po.sys.OemInfoPo;
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
