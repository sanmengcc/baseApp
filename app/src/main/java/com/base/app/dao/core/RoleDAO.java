package com.base.app.dao.core;

import com.base.app.po.core.RolePo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDAO extends BaseDAO<RolePo> {
}
