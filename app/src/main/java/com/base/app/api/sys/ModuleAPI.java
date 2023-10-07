package com.base.app.api.sys;

import com.base.app.ro.module.*;
import com.base.app.service.SystemModuleService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Api(name = "系统菜单模块")
@RestController
@RequestMapping(value = "/api/module")
public class ModuleAPI extends BaseAPI {

    @Resource
    private SystemModuleService systemModuleService;

    @Api(name = "新增系统菜单")
    @PostMapping(value = "/addModule")
    public R addModule(@RequestBody AddRo ro) {
        systemModuleService.addModule(ro);
        return R.ok();
    }

    @Api(name = "分页查询系统菜单")
    @GetMapping(value = "/searchPage")
    public R searchPage(SearchRo ro) {
        return R.ok(systemModuleService.searchPage(ro));
    }

    @Api(name = "获取子菜单列表")
    @ApiPermission(auth = false)
    @GetMapping(value = "/selectByParentId")
    public R selectByParentId(String parentId) {
        return R.ok(systemModuleService.selectByParentId(parentId));
    }

    @Api(name = "获取菜单详情")
    @GetMapping(value = "/selectById")
    public R selectById(String moduleId) {
        return R.ok(systemModuleService.selectById(moduleId));
    }

    @Api(name = "更新菜单详情")
    @PostMapping(value = "/updateById")
    public R updateById(@RequestBody EditRo editRo) {
        this.systemModuleService.update(editRo);
        return R.ok();
    }

    @Api(name = "删除菜单详情")
    @PostMapping(value = "/delete")
    public R delete(@RequestBody DeleteRo ro) {
        this.systemModuleService.delete(ro.getModuleId());
        return R.ok();
    }

    @Api(name = "变更菜单状态")
    @PostMapping(value = "/change")
    public R change(@RequestBody ChangeRo changeRo) {
        this.systemModuleService.change(changeRo);
        return R.ok();
    }

    @Api(name = "获取菜单树")
    @ApiPermission(auth = false)
    @GetMapping(value = "/selectTree")
    public R selectTree() {
        return R.ok(systemModuleService.selectTree());
    }

}
