package com.base.app.api.sys;

import com.base.app.ro.host.AddRo;
import com.base.app.ro.host.DeleteRo;
import com.base.app.ro.host.EditRo;
import com.base.app.ro.host.SearchRo;
import com.base.app.service.HostConfigService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import com.base.util.ServletUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Api(name = "配置中心")
@RestController
@RequestMapping(value = "/api/config")
public class ConfigAPI extends BaseAPI {

    @Resource
    private HostConfigService hostConfigService;

    @Api(name = "获取域名配置信息")
    @ApiPermission(auth = false,login = false)
    @GetMapping(value = "/getHostConfig")
    public R getHostConfig() {
        String host = ServletUtils.getRefererHost();
        log.error("getHostConfig Host:{}", host);
        return R.ok(hostConfigService.getHostConfig(host));
    }

    @Api(name = "分页查询域名配置列表")
    @GetMapping(value = "/searchPage")
    public R searchPage(SearchRo ro) {
        return R.ok(this.hostConfigService.searchPage(ro));
    }

    @Api(name = "删除域名配置")
    @PostMapping(value = "/delete")
    public R delete(@RequestBody DeleteRo ro) {
        this.hostConfigService.delete(ro.getConfigId());
        return R.ok();
    }

    @Api(name = "新增域名配置")
    @PostMapping(value = "/addConfig")
    public R addConfig(@RequestBody AddRo ro) {
        this.hostConfigService.addConfig(ro);
        return R.ok();
    }

    @Api(name = "修改域名配置")
    @PostMapping(value = "/updateById")
    public R updateById(@RequestBody EditRo ro) {
        this.hostConfigService.update(ro);
        return R.ok();
    }

    @Api(name = "查询域名配置详情")
    @GetMapping(value = "/selectById")
    public R selectById(Long configId) {
        return R.ok(this.hostConfigService.selectById(configId));
    }
}
