package com.base.app.api.sys;

import com.base.app.ro.oem.AddRo;
import com.base.app.ro.oem.DeleteRo;
import com.base.app.ro.oem.EditRo;
import com.base.app.ro.oem.SearchRo;
import com.base.app.service.OemInfoService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
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
    @PostMapping(value = "/addOem")
    public R addOem(@RequestBody AddRo ro) {
        oemInfoService.addOem(ro);
        return R.ok();
    }

    @Api(name = "修改租户")
    @PostMapping(value = "/updateById")
    public R updateById(@RequestBody EditRo ro) {
        oemInfoService.update(ro);
        return R.ok();
    }

    @Api(name = "删除租户")
    @PostMapping(value = "/delete")
    public R delete(@RequestBody DeleteRo ro) {
        oemInfoService.delete(ro.getOemId());
        return R.ok();
    }

    @Api(name = "分页查询租户")
    @GetMapping(value = "/searchPage")
    public R searchPage(SearchRo ro) {
        return R.ok(oemInfoService.searchPage(ro));
    }

    @Api(name = "查询租户详情")
    @GetMapping(value = "/selectById")
    public R selectById(Long oemId) {
        return R.ok(oemInfoService.selectById(oemId));
    }

    @Api(name = "查询租户列表")
    @ApiPermission(auth = false)
    @GetMapping(value = "/selectList")
    public R selectList() {
        return R.ok(this.oemInfoService.selectList());
    }

}
