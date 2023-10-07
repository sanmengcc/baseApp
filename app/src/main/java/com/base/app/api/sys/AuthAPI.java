package com.base.app.api.sys;

import com.base.app.service.TokenService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.api.BaseAPI;
import com.base.core.context.CloudManager;
import com.base.core.entity.R;
import com.base.core.entity.UserInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(name = "权限模块")
@RestController
@RequestMapping(value = "/api/auth")
public class AuthAPI extends BaseAPI {

    @Resource
    private TokenService tokenService;


    @Api(name = "获取用户Token信息")
    @ApiPermission(auth = false)
    @GetMapping(value = "/getUserInfo")
    public R getUserInfo() {
        String token = CloudManager.getInstance().getToken();
        UserInfo userInfo = tokenService.getToken(token);
        return R.ok(userInfo);
    }
}
