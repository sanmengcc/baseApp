package com.base.app.api.sys;

import com.base.app.ro.role.*;
import com.base.app.service.RoleService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(name = "角色模块")
@RestController
@RequestMapping(value = "/api/role")
public class RoleAPI extends BaseAPI {

    @Resource
    private RoleService roleService;

    @Api(name = "新增角色")
    @PostMapping(value = "/addRole")
    public R addRole(@RequestBody AddRo ro) {
        roleService.addRole(ro);
        return R.ok();
    }

    @Api(name = "修改角色")
    @PostMapping(value = "/updateById")
    public R updateRole(@RequestBody EditRo ro) {
        this.roleService.updateRole(ro);
        return R.ok();
    }

    @Api(name = "删除角色")
    @PostMapping(value = "/delete")
    public R delete(@RequestBody DeleteRo ro) {
        this.roleService.delete(ro.getRoleId());
        return R.ok();
    }

    @Api(name = "查询角色详情")
    @GetMapping(value = "/selectById")
    public R selectById(Long roleId) {
        return R.ok(this.roleService.selectById(roleId));
    }

    @Api(name = "分页查询角色")
    @GetMapping(value = "/searchPage")
    public R searchPage(SearchRo ro) {
        return R.ok(this.roleService.searchPage(ro));
    }


    @Api(name = "分配角色")
    @PostMapping(value = "/allotRole")
    public R allotRole(@RequestBody AllotRoleRo ro) {
        this.roleService.allotRole(ro.getStaffId(), ro.getRoleIdList());
        return R.ok();
    }

    @Api(name = "查询员工分配的角色")
    @GetMapping(value = "/selectStaffRole")
    @ApiPermission(auth = false)
    public R selectStaffRole(String staffId) {
        return R.ok(this.roleService.selectStaffRole(staffId));
    }

    @Api(name = "查询角色列表")
    @GetMapping(value = "/selectList")
    @ApiPermission(auth = false)
    public R selectList() {
        return R.ok(this.roleService.selectList());
    }

    @Api(name = "授权系统菜单")
    @PostMapping(value = "/authModule")
    public R authModule(@RequestBody ModuleRoleRo ro) {
        this.roleService.authModule(ro.getRoleId(), ro.getModuleIdList());
        return R.ok();
    }

    @Api(name = "查询角色拥有的菜单ID")
    @ApiPermission(auth = false)
    @GetMapping(value = "/selectRoleHasModuleId")
    public R selectRoleHasModuleId(Long roleId){
        return R.ok(this.roleService.selectRoleHasModuleId(roleId));
    }

}
