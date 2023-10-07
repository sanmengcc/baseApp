package com.base.app.service.impl;

import com.base.app.dao.sys.RoleDAO;
import com.base.app.dao.sys.RoleModuleDAO;
import com.base.app.dao.sys.StaffRoleDAO;
import com.base.app.dto.role.RoleDTO;
import com.base.app.po.sys.RoleModulePo;
import com.base.app.po.sys.RolePo;
import com.base.app.po.sys.StaffRolePo;
import com.base.app.ro.role.AddRo;
import com.base.app.ro.role.EditRo;
import com.base.app.ro.role.SearchRo;
import com.base.app.service.RoleService;
import com.base.core.entity.Page;
import com.base.core.entity.Paging;
import com.base.util.JsonUtils;
import com.base.util.ValidateHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDAO roleDAO;
    @Resource
    private StaffRoleDAO staffRoleDAO;
    @Resource
    private RoleModuleDAO roleModuleDAO;

    @Override
    public void addRole(AddRo ro) {
        RolePo rolePo = JsonUtils.createBean(ro, RolePo.class);
        rolePo.setGmtCreate(new Date());
        this.roleDAO.add(rolePo);
    }

    @Override
    public void updateRole(EditRo ro) {
        this.roleDAO.update(JsonUtils.createBean(ro, RolePo.class));
    }

    @Override
    public RoleDTO selectById(Long roleId) {
        return JsonUtils.createBean(this.roleDAO.queryById(roleId), RoleDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Long roleId) {
        this.roleDAO.delete(roleId);
        // 删除关联关系
        this.roleModuleDAO.deleteRole(roleId);
        this.staffRoleDAO.deleteRole(roleId);
    }

    @Override
    public Page<RoleDTO> searchPage(SearchRo ro) {
        // 设置分页参数
        Paging paging = Paging.build(ro);
        // 总数查询
        Long count = roleDAO.selectCount(paging);
        // 设置偏移量
        paging.setRowCount(count);
        // 分页查询
        return paging.search(roleDAO::selectPage, RoleDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void allotRole(String staffId, Long[] roleIdList) {
        staffRoleDAO.deleteStaffId(staffId);
        if (roleIdList.length > 0) {
            List<StaffRolePo> dataList = new ArrayList<>();
            for (Long roleId : roleIdList) {
                StaffRolePo po = new StaffRolePo();
                po.setRoleId(roleId);
                po.setStaffId(staffId);
                po.setGmtCreate(new Date());
                dataList.add(po);
            }
            this.staffRoleDAO.addList(dataList);
        }
    }

    @Override
    public List<RoleDTO> selectStaffRole(String staffId) {
        return this.staffRoleDAO.selectStaffRole(staffId);
    }

    @Override
    public List<RoleDTO> selectList() {
        return JsonUtils.createList(this.roleDAO.selectNoPage(new Paging()), RoleDTO.class);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void authModule(Long roleId, String[] moduleIdList) {
        this.roleModuleDAO.deleteRole(roleId);
        List<RoleModulePo> dataList = new ArrayList<>();
        for (String moduleId : moduleIdList) {
            RoleModulePo po = new RoleModulePo();
            po.setGmtCreate(new Date());
            po.setModuleId(moduleId);
            po.setRoleId(roleId);
            dataList.add(po);
        }
        if (ValidateHelper.isNotEmptyCollection(dataList)) {
            this.roleModuleDAO.addList(dataList);
        }
    }

    @Override
    public List<String> selectRoleHasModuleId(Long roleId) {
        return this.roleModuleDAO.selectRoleHasModuleId(roleId);
    }

    @Override
    public List<String> selectStaffHasModule(String staffId) {
        return this.roleModuleDAO.selectStaffHasModule(staffId);
    }

}
