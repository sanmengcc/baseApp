package com.base.app.dao.core;

import com.base.app.po.core.UserLoginLogPo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginLogDAO extends BaseDAO<UserLoginLogPo> {
}
