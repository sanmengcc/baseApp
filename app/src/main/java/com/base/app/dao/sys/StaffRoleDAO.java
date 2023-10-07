package com.base.app.dao.sys;

import com.base.app.dto.role.RoleDTO;
import com.base.app.po.sys.StaffRolePo;
import com.base.core.mybatis.BaseDAO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StaffRoleDAO extends BaseDAO<StaffRolePo> {

    /**
     * 按员工ID删除
     * @param staffId
     */
    void deleteStaffId(String staffId);

    /**
     * 查询员工分配的角色
     * @param staffId
     * @return
     */
    List<RoleDTO> selectStaffRole(String staffId);

    /**
     * 按角色ID删除
     * @param roleId
     */
    void deleteRole(Long roleId);
}
