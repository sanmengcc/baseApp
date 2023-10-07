package com.base.app.api.sys;

import com.base.app.ro.log.LoginLogPageRo;
import com.base.app.service.UserService;
import com.base.core.annotation.Api;
import com.base.core.api.BaseAPI;
import com.base.core.entity.PageRo;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(name = "登陆日志模块")
@RestController
@RequestMapping(value = "/api/loginLog")
public class LoginLogAPI extends BaseAPI {

    @Resource
    private UserService userService;

    @Api(name = "查询登陆日志列表")
    @GetMapping(value = "/getLogPage")
    public R getLogPage(LoginLogPageRo ro, PageRo pageRo) {
        return R.ok(userService.selectLoginLogPage(ro, pageRo));
    }

}
