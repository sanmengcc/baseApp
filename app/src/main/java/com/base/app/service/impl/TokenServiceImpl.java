package com.base.app.service.impl;

import com.base.app.config.encrypt.TokenConfig;
import com.base.app.dto.user.LoginTokenDTO;
import com.base.app.dto.user.LoginUserDTO;
import com.base.app.dto.user.UserPermissionsDTO;
import com.base.app.service.AuthService;
import com.base.app.service.RedisService;
import com.base.app.service.TokenService;
import com.base.core.entity.UserInfo;
import com.base.util.JsonUtils;
import com.base.util.UUIDGenerator;
import com.base.util.ValidateHelper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    private TokenConfig tokenConfig;
    @Resource
    private RedisService redisService;
    @Resource
    private AuthService authService;

    @Override
    public LoginTokenDTO generateToken(LoginUserDTO userDTO) {
        String tokenId = UUIDGenerator.generate();
        String tokenUserId = userDTO.getUserGlobalId();
        UserInfo userInfo = JsonUtils.createBean(userDTO, UserInfo.class);
        redisService.setValue(tokenConfig.getTokenPrex() + ":" + tokenId, userDTO.getUserGlobalId(), tokenConfig.getExpires());
        redisService.setValue(tokenConfig.getTokenPrexUser() + ":" + tokenUserId, JsonUtils.toJson(userInfo), tokenConfig.getExpires());
        this.setPermissionURL(tokenId, userDTO.getUserGlobalId());
        LoginTokenDTO tokenDTO = new LoginTokenDTO();
        tokenDTO.setAccess_token(tokenId);
        tokenDTO.setExpires_in(tokenConfig.getExpires());
        return tokenDTO;
    }

    @Override
    public UserInfo getToken(String tokenId) {
        String userGlobalId = (String) redisService.getValue(tokenConfig.getTokenPrex() + ":" + tokenId);
        if (ValidateHelper.isEmptyString(userGlobalId)) {
            return null;
        }
        Object userInfo = redisService.getValue(tokenConfig.getTokenPrexUser() + ":" + userGlobalId);
        return JsonUtils.str2Bean(userInfo.toString(), UserInfo.class);
    }

    @Override
    public void logout(String token) {
        UserInfo userInfo = this.getToken(token);
        redisService.delete(tokenConfig.getTokenPrex() + ":" + token);
        redisService.delete(tokenConfig.getTokenPrexUser() + ":" + userInfo.getUserGlobalId());
    }

    /**
     * 设置权限
     * @param token
     * @param userGlobalId
     */
    public void setPermissionURL(String token, String userGlobalId) {
        UserPermissionsDTO permissionsDTO = authService.loginByUserGlobalId(userGlobalId);
        redisService.setValue(tokenConfig.getPrexPermission() + ":" + token, JsonUtils.toJson(permissionsDTO.getRequestURL()), tokenConfig.getExpires());
    }

    @Override
    public List<String> getPermissionURL(String token) {
        Object value = redisService.getValue(tokenConfig.getPrexPermission() + ":" + token);
        if (Objects.isNull(value)) {
            return new ArrayList<>();
        }
        return JsonUtils.str2List(value.toString(), String.class);
    }
}
