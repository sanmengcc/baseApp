package com.base.app.api.sys;

import com.base.app.dto.log.SqlLogDTO;
import com.base.app.ro.log.SQLDeleteRo;
import com.base.app.ro.log.SQLLogPageRo;
import com.base.app.service.SQLLogService;
import com.base.core.annotation.Api;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(name = "SQL日志模块")
@RestController
@RequestMapping(value = "/api/sqlLog")
public class SQLLogAPI extends BaseAPI {

    @Resource
    private SQLLogService sqlLogService;

    @Api(name = "分页查询SQL日志")
    @GetMapping(value = "/searchPage")
    public R searchPage(SQLLogPageRo ro) {
        return R.ok(sqlLogService.searchPage(ro));
    }

    @Api(name = "查询日志详情")
    @GetMapping(value = "/selectById")
    public R selectById(Long logId) {
        return R.ok(sqlLogService.selectById(logId));
    }

    @Api(name = "删除日志")
    @PostMapping(value = "/delete")
    public R delete(@RequestBody SQLDeleteRo ro) {
        this.sqlLogService.delete(ro.getKey());
        return R.ok();
    }
}
