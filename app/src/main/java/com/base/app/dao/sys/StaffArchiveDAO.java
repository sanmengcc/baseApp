package com.base.app.dao.sys;

import com.base.app.po.sys.StaffArchivePo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StaffArchiveDAO extends BaseDAO<StaffArchivePo> {

    /**
     * 按员工ID查询档案
     * @param staffId
     * @return
     */
    StaffArchivePo selectByStaffId(String staffId);
}
