package com.base.core.interceptor;

import com.base.core.constant.SystemConstants;
import com.base.core.context.CloudManager;
import com.base.core.entity.R;
import com.base.util.JsonUtils;
import com.base.util.UUIDGenerator;
import com.base.util.ValidateHelper;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseAuthInterceptor {

    /**
     * 登录失效
     *
     * @param response
     */
    public void loginFailure(ServletResponse response) {
        Map data = new HashMap();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        data.put("code", "600");
        data.put("desc", "当前登陆已失效!");
        try {
            response.getWriter().println(JsonUtils.toJson(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置错误提示
     * @param response
     * @param r
     */
    public void failure(ServletResponse response, R r) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().println(JsonUtils.toJson(r));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成track
     *
     * @param request
     */
    protected void setTrack(HttpServletRequest request) {
        String trackId = request.getHeader(SystemConstants.HttpHeader.TRACE_ID);
        if (ValidateHelper.isEmptyString(trackId)) {
            trackId = UUIDGenerator.generate();
            //处理MDC
            MDC.put(SystemConstants.HttpHeader.TRACE_ID, trackId);
            //处理本地线程
            CloudManager.getInstance().setTraceId(trackId);
        }
    }

    /**
     * 对请求删除track
     */
    protected void removeTrack() {
        MDC.remove(SystemConstants.HttpHeader.TRACE_ID);
    }
}
