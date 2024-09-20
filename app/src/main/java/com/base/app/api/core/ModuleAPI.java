package com.base.app.api.core;

import com.base.app.ro.core.module.*;
import com.base.app.service.core.SystemModuleService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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
    @PostMapping(value = "/add")
    public R add(@Valid @RequestBody AddRo ro) {
        systemModuleService.addModule(ro);
        return R.ok();
    }

    @Api(name = "分页查询系统菜单")
    @GetMapping(value = "/page")
    public R page(@Valid SearchRo ro) {
        return R.ok(systemModuleService.searchPage(ro));
    }

    @Api(name = "获取子菜单列表")
    @ApiPermission(auth = false)
    @GetMapping(value = "/list")
    public R list(String parentId) {
        return R.ok(systemModuleService.selectByParentId(parentId));
    }

    @Api(name = "获取菜单详情")
    @GetMapping(value = "/info")
    public R info(String moduleId) {
        return R.ok(systemModuleService.selectById(moduleId));
    }

    @Api(name = "更新菜单详情")
    @PostMapping(value = "/edit")
    public R edit(@Valid @RequestBody EditRo editRo) {
        this.systemModuleService.update(editRo);
        return R.ok();
    }

    @Api(name = "删除菜单详情")
    @PostMapping(value = "/delete")
    public R delete(@Valid @RequestBody DeleteRo ro) {
        this.systemModuleService.delete(ro.getModuleId());
        return R.ok();
    }

    @Api(name = "变更菜单状态")
    @PostMapping(value = "/change")
    public R change(@Valid @RequestBody ChangeRo changeRo) {
        this.systemModuleService.change(changeRo);
        return R.ok();
    }

    @Api(name = "获取菜单树")
    @ApiPermission(auth = false)
    @GetMapping(value = "/tree")
    public R tree() {
        return R.ok(systemModuleService.selectTree());
    }

}
