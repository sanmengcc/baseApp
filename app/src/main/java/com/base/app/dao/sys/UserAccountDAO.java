package com.base.app.dao.sys;

import com.base.app.po.sys.UserAccountPo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAccountDAO extends BaseDAO<UserAccountPo> {

    /**
     * 根据账号查询凭证信息
     * @param account
     * @return
     */
    UserAccountPo selectByAccount(String account);

    /**
     * 按用户全局ID查询
     * @param userGlobalId
     * @return
     */
    List<UserAccountPo> selectUserGlobalId(String userGlobalId);

    /**
     * 按用户ID删除
     * @param userGlobalId
     */
    void deleteUserGlobalId(String userGlobalId);
}
