package com.base.app.api.core;

import com.base.app.ro.core.log.ApiDeleteRo;
import com.base.app.ro.core.log.ApiLogPageRo;
import com.base.app.service.core.RequestLogService;
import com.base.core.annotation.Api;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(name = "接口日志模块")
@RestController
@RequestMapping(value = "/api/requestLog")
public class RequestLogAPI extends BaseAPI {

    @Resource
    private RequestLogService requestLogService;

    @Api(name = "分页查询接口日志")
    @GetMapping(value = "/page")
    public R page(ApiLogPageRo ro) {
        return R.ok(this.requestLogService.searchPage(ro));
    }

    @Api(name = "删除接口日志")
    @PostMapping(value = "/delete")
    public R delete(@RequestBody ApiDeleteRo ro) {
        this.requestLogService.delete(ro.getKey());
        return R.ok();
    }
}
