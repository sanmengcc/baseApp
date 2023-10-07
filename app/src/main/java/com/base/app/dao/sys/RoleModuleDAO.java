package com.base.app.dao.sys;

import com.base.app.po.sys.RoleModulePo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleModuleDAO extends BaseDAO<RoleModulePo> {

    /**
     * 按角色ID刪除
     * @param roleId
     */
    void deleteRole(Long roleId);

    /**
     * 查询角色拥有的菜单
     * @param roleId
     * @return
     */
    List<String> selectRoleHasModuleId(Long roleId);

    /**
     * 查询员工拥有的菜单ID
     * @param staffId
     * @return
     */
    List<String> selectStaffHasModule(String staffId);
}
