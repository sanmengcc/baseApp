package com.base.app.api.sys;

import com.base.app.dto.user.UserPermissionsDTO;
import com.base.app.ro.user.ChangePasswordAdminRo;
import com.base.app.ro.user.SearchRo;
import com.base.app.service.AuthService;
import com.base.app.service.UserService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.api.BaseAPI;
import com.base.core.context.CloudManager;
import com.base.core.entity.R;
import com.base.core.entity.UserInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(name = "用户中心")
@RestController
@RequestMapping(value = "/api/userCenter")
public class UserCenterAPI extends BaseAPI {

    @Resource
    private UserService userService;
    @Resource
    private AuthService authService;

    @Api(name = "管理员修改用户密码")
    @PostMapping(value = "/changePasswordByAdmin")
    public R changePasswordByAdmin(@RequestBody ChangePasswordAdminRo ro) {
        userService.updatePasswordByAdmin(ro.getUserGlobalId(), ro.getPassword());
        return R.ok();
    }

    @Api(name = "分页查询用户列表")
    @GetMapping(value = "/searchPage")
    public R searchPage(SearchRo ro) {
        return R.ok(userService.searchPage(ro));
    }

    @Api(name = "获取用户权限")
    @ApiPermission(auth = false)
    @GetMapping(value = "/getPermissions")
    public R getPermissions() {
        UserInfo userInfo = CloudManager.getInstance().getUserInfo();
        UserPermissionsDTO permissionsDTO = authService.loginByUserGlobalId(userInfo.getUserGlobalId());
        return R.ok(permissionsDTO);
    }
}
