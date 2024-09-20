package com.base.app.api.core;

import com.base.app.ro.core.oem.AddRo;
import com.base.app.ro.core.oem.DeleteRo;
import com.base.app.ro.core.oem.EditRo;
import com.base.app.ro.core.oem.SearchRo;
import com.base.app.service.core.OemInfoService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(name = "租户管理模块")
@RestController
@RequestMapping(value = "/api/oemInfo")
public class OemInfoAPI {

    @Resource
    private OemInfoService oemInfoService;


    @Api(name = "新增租户")
    @PostMapping(value = "/add")
    public R add(@Valid @RequestBody AddRo ro) {
        oemInfoService.addOem(ro);
        return R.ok();
    }

    @Api(name = "修改租户")
    @PostMapping(value = "/edit")
    public R edit(@Valid @RequestBody EditRo ro) {
        oemInfoService.update(ro);
        return R.ok();
    }

    @Api(name = "删除租户")
    @PostMapping(value = "/delete")
    public R delete(@Valid @RequestBody DeleteRo ro) {
        oemInfoService.delete(ro.getOemId());
        return R.ok();
    }

    @Api(name = "分页查询租户")
    @GetMapping(value = "/page")
    public R page(@Valid SearchRo ro) {
        return R.ok(oemInfoService.searchPage(ro));
    }

    @Api(name = "查询租户详情")
    @GetMapping(value = "/info")
    public R info(Long oemId) {
        return R.ok(oemInfoService.selectById(oemId));
    }

    @Api(name = "查询租户列表")
    @ApiPermission(auth = false)
    @GetMapping(value = "/list")
    public R list() {
        return R.ok(this.oemInfoService.selectList());
    }

}
