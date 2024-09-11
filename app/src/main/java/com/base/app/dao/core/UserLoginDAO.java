package com.base.app.dao.core;

import com.base.app.po.core.UserLoginPo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserLoginDAO extends BaseDAO<UserLoginPo> {
}
