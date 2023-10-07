package com.base.app.api.sys;

import com.base.app.service.CaptchaService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Api(name = "验证码")
@RestController
@RequestMapping(value = "/api/userCenter/captcha")
public class CaptchaAPI extends BaseAPI {

    @Resource
    private CaptchaService captchaService;


    @Api(name = "获取图片验证码")
    @ApiPermission(auth = false,login = false)
    @GetMapping(value = "/captchaImage")
    public R getCaptchaCode() {
        return R.ok(captchaService.getCaptchaImage());
    }
}
