package com.base.app.config.interceptor;

import com.base.app.config.encrypt.NeedSign;
import com.base.app.config.encrypt.RequestWrapper;
import com.base.app.config.encrypt.SignUtil;
import com.base.app.service.TokenService;
import com.base.core.annotation.ApiPermission;
import com.base.core.constant.SystemConstants;
import com.base.core.context.CloudManager;
import com.base.core.entity.R;
import com.base.core.entity.UserInfo;
import com.base.core.interceptor.BaseAuthInterceptor;
import com.base.util.JsonUtils;
import com.base.util.MD5Utils;
import com.base.util.ServletUtils;
import com.base.util.ValidateHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
@Configuration
public class AuthInterceptor extends BaseAuthInterceptor implements HandlerInterceptor {

    @Resource
    private NeedSign needSign;
    @Resource
    private SignUtil signUtil;
    @Resource
    private TokenService tokenService;

    private static List<String> ignoreWhite = new ArrayList<>();

    static {
        // 手动设置白名单
        ignoreWhite.add("/error");
    }

    /**
     * 请求处理之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        CloudManager.getInstance().clear();
    }

    /**
     * 请求处理之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 进行签名验证
        if (needSign.getSign()) {
            // 获取请求参数
            LinkedHashMap paramsMap = getRequestParameter(request);
            String originSign = (String) paramsMap.get(needSign.getSignKey());
            if (ValidateHelper.isEmptyString(originSign)) {
                super.failure(response, R.error("签名字符串不存在!"));
                return false;
            }

            String sign = signUtil.createSign(paramsMap);
            if (!sign.equalsIgnoreCase(originSign)) {
                super.failure(response, R.error("签名验证失败!"));
                return false;
            }
        }

        // 进行接口验证
        String requestURI = request.getRequestURI();
        String token = ServletUtils.getHeader(SystemConstants.HttpHeader.AUTHORIZATION);
        String oemCode = ServletUtils.getHeader(SystemConstants.HttpHeader.OEM_CODE);
        CloudManager.getInstance().setOemCode(oemCode);

        // token权限认证
        this.setTrack(request);

        if (ignoreWhite.contains(requestURI)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        ApiPermission annotation = method.getAnnotation(ApiPermission.class);
        if (Objects.nonNull(annotation)) {
            // 无需登陆无需校验权限
            if (!annotation.auth() && !annotation.login()) {
                return true;
            }
            // 接口需要登陆但是没有Token
            if (annotation.login() && ValidateHelper.isEmptyString(token)) {
                super.loginFailure(response);
                return false;
            }
            // 接口不需要校验权限但是没有登陆
            if (!annotation.auth() && ValidateHelper.isEmptyString(token)) {
                super.loginFailure(response);
                return false;
            }
            if (annotation.auth() && ValidateHelper.isEmptyString(token)) {
                super.loginFailure(response);
                return false;
            }
            // 接口需要校验权限
            if (!annotation.auth() && ValidateHelper.isNotEmptyString(token)) {
                return setUserInfo(token, response);
            }
        }
        if (ValidateHelper.isEmptyString(token)) {
            super.loginFailure(response);
            return false;
        }
        // 默认需要登陆
        List<String> permissionURL = tokenService.getPermissionURL(token);

        // 判断权限
        if (!permissionURL.contains(requestURI)) {
            super.failure(response, R.error("401","无权限访问!"));
            return false;
        }

        return setUserInfo(token, response);

    }


    public boolean setUserInfo(String token, HttpServletResponse response) {
        CloudManager cloudManager = CloudManager.getInstance();
        cloudManager.setToken(token);
        UserInfo userInfo = tokenService.getToken(token);
        if (Objects.isNull(userInfo)) {
            //登陆失败
            super.loginFailure(response);
            return false;
        }
        //解析用户数据传入上下文
        cloudManager.setUserInfo(userInfo);
        return true;
    }

    /**
     * 获取请求参数
     *
     * @param request
     * @return
     */
    public LinkedHashMap getRequestParameter(HttpServletRequest request) {

        try {
            String strParam = new RequestWrapper(request).getBodyString();
            Map<String, Object> param = new HashMap();
            ObjectMapper mapper = new ObjectMapper();
            if (ValidateHelper.isNotEmptyString(strParam)) {
                mapper.readValue(strParam, Map.class)
                        .forEach((k, v) -> {
                            param.put(k.toString(), v);
                        });
            }
            Map<String, String[]> parameterMap = request.getParameterMap();
            parameterMap.forEach((k, v) -> {
                if (v != null && v.length == 1) {
                    param.put(k, v[0]);
                } else {
                    param.put(k, v);
                }
            });

            Object[] keys = param.keySet().toArray();
            Arrays.sort(keys);
            LinkedHashMap hashMap = new LinkedHashMap();
            for (Object key : keys) {
                hashMap.put(key, param.get(key));
            }

            return hashMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}