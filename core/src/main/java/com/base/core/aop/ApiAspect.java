package com.base.core.aop;

import com.base.core.annotation.Api;
import com.base.core.constant.CommonConstants;
import com.base.core.context.CloudManager;
import com.base.core.entity.dto.RequestLog;
import com.base.core.entity.UserInfo;
import com.base.core.exception.CloudException;
import com.base.core.service.LogService;
import com.base.util.IpUtils;
import com.base.util.JsonUtils;
import com.base.util.ServletUtils;
import com.base.util.ValidateHelper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * 接口全局aop
 */
@Component
@Aspect
@Slf4j
public class ApiAspect {

    @Resource
    private LogService logService;

    /**
     * aroud切面
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(public * com.base.*.api..*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        // 接口日志
        RequestLog requestLog = new RequestLog();
        requestLog.setIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        requestLog.setAccount(Optional.ofNullable(CloudManager.getInstance().getUserInfo()).map(UserInfo::getAccount).orElse(null));
        Class<?> classTarget = pjp.getTarget().getClass();
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 调用方法
        Method targetMethod = methodSignature.getMethod();
        // 类全名
        String targetMethodFullName = pjp.getTarget().getClass().getName() + "." + targetMethod.getName();
        // 设置请求的方法
        requestLog.setMethod(targetMethodFullName);
        //请求的参数
        requestLog.setRequestData(this.getRequestJson(pjp));
        // 中文方法名称
        String methodName = "";
        Api classApi = AnnotationUtils.findAnnotation(classTarget, Api.class);
        if (Objects.nonNull(classApi)) {
            methodName = classApi.name();
            if (methodSignature.getDeclaringType().isAnnotationPresent(Api.class)) {
                Api methodApi = AnnotationUtils.findAnnotation(methodSignature.getMethod(), Api.class);
                methodName = methodName + "-" + methodApi.name();
            }
        }
        requestLog.setMethodName(methodName);
        requestLog.setResult(CommonConstants.Flag.SUCCESS);
        Long startTime = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();
            requestLog.setResponseData(JsonUtils.toJson(result));
            return result;
        } catch (CloudException e) {
            requestLog.setResponseData(e.getMessage());
            requestLog.setResult(CommonConstants.Flag.FAIL);
            throw e;
        } catch (Exception e) {
            requestLog.setResponseData(e.getMessage());
            requestLog.setResult(CommonConstants.Flag.FAIL);
            throw e;
        } finally {
            Long endTime = System.currentTimeMillis();
            Long useTime = endTime - startTime;
            requestLog.setUseTime(useTime);
            logService.saveLog(requestLog);
        }
    }

    /**
     * 获取请求参数
     *
     * @param pjp
     * @return
     */
    public String getRequestJson(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }
        //将参数所在的数组转换成json
        String params = "";
        if (arguments != null) {
            try {
                params = JsonUtils.toJson(arguments);
            } catch (Exception e) {
                params = arguments.toString();
            }
        }
        return params;
    }

    private String toJSONStr(Object[] args) {
        try {
            String argsStr = JsonUtils.toJson(args);
            return ValidateHelper.isNotEmptyString(argsStr) && argsStr.length() > 500 ? argsStr.substring(0, 500) + "..." : argsStr;
        } catch (Exception var3) {
            log.error("参数转换成JSON失败:{}", args);
            return "";
        }
    }
}
