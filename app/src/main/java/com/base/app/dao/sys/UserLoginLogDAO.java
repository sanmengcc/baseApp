package com.base.app.dao.sys;

import com.base.app.po.sys.UserLoginLogPo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginLogDAO extends BaseDAO<UserLoginLogPo> {
}
