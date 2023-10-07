package com.base.app.dao.sys;

import com.base.app.po.sys.UserLoginPo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginDAO extends BaseDAO<UserLoginPo> {
}
