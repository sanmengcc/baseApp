package com.base.app.dao.sys;

import com.base.app.po.sys.UserPo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO extends BaseDAO<UserPo> {

    /**
     * 根据用户全局ID查询
     * @param userGlobalId
     * @return
     */
    UserPo selectByGlobalId(String userGlobalId);

    /**
     * 修改用户密码
     * @param userGlobalId
     * @param password
     */
    void changePassword(String userGlobalId, String password);
}
