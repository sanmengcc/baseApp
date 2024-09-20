package com.base.app.api.core;

import com.base.app.ro.core.log.LoginLogPageRo;
import com.base.app.service.core.UserService;
import com.base.core.annotation.Api;
import com.base.core.api.BaseAPI;
import com.base.core.entity.PageRo;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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
    @GetMapping(value = "/page")
    public R page(LoginLogPageRo ro, @Valid PageRo pageRo) {
        return R.ok(userService.selectLoginLogPage(ro, pageRo));
    }

}
