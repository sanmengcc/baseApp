package com.base.app.service;

import com.base.app.dto.module.SystemModuleDTO;
import com.base.app.dto.role.RoleDTO;
import com.base.app.ro.role.AddRo;
import com.base.app.ro.role.EditRo;
import com.base.app.ro.role.SearchRo;
import com.base.core.entity.Page;

import java.util.List;

public interface RoleService {

    /**
     * 新增角色
     * @param ro
     */
    void addRole(AddRo ro);

    /**
     * 修改角色
     * @param ro
     */
    void updateRole(EditRo ro);

    /**
     * 查看角色详情
     * @param roleId
     * @return
     */
    RoleDTO selectById(Long roleId);

    /**
     * 删除角色
     * @param roleId
     */
    void delete(Long roleId);

    /**
     * 分页查询角色列表
     * @param ro
     * @return
     */
    Page<RoleDTO> searchPage(SearchRo ro);

    /**
     * 分配角色
     * @param staffId
     * @param roleIdList
     */
    void allotRole(String staffId, Long[] roleIdList);

    /**
     * 查询员工分配的角色
     * @param staffId
     * @return
     */
    List<RoleDTO> selectStaffRole(String staffId);

    /**
     * 查询角色列表
     * @return
     */
    List<RoleDTO> selectList();

    /**
     * 授权系统菜单
     * @param roleId
     * @param moduleIdList
     */
    void authModule(Long roleId, String[] moduleIdList);

    /**
     * 查询角色拥有的菜单
     * @param roleId
     * @return
     */
    List<String> selectRoleHasModuleId(Long roleId);

    /**
     * 查询员工拥有的菜单
     * @param staffId
     * @return
     */
    List<String> selectStaffHasModule(String staffId);
}
