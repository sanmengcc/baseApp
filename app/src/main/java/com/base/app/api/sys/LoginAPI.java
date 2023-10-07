package com.base.app.api.sys;

import com.base.app.dto.user.LoginTokenDTO;
import com.base.app.service.TokenService;
import com.base.app.service.UserLoginService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.api.BaseAPI;
import com.base.core.context.CloudManager;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Api(name = "用户登陆")
@RestController
@RequestMapping(value = "/api/oauth")
public class LoginAPI extends BaseAPI {

    @Resource
    private UserLoginService userLoginService;
    @Resource
    private TokenService tokenService;

    @Api(name = "用户登录")
    @ApiPermission(auth = false,login = false)
    @PostMapping(value = "/Login")
    public R userLogin(@RequestParam Map<String, String> parameters, @RequestBody Map<String, String> jsonData) {
        parameters.putAll(jsonData);
        LoginTokenDTO tokenDTO = userLoginService.userLogin(parameters);
        return R.ok(tokenDTO);
    }

    @Api(name = "退出登陆")
    @ApiPermission(auth = false)
    @GetMapping(value = "/logout")
    public R logout() {
        String token = CloudManager.getInstance().getToken();
        tokenService.logout(token);
        return R.ok();
    }
}
