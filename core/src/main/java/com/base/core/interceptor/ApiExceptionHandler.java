package com.base.core.interceptor;

import com.base.core.entity.R;
import com.base.core.exception.CloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * api 异常拦截
 */
@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public R httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error("请求方式不支持");
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public R handleBindException(BindException e) {
        return R.error(e.getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R handleException(Exception e) {
        log.error(e.getMessage());
        return R.error("服务器异常");
    }

    @ExceptionHandler(CloudException.class)
    @ResponseBody
    public R handleCloudException(CloudException e) {
        log.error(e.getMessage());
        return R.error(e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public R handleSQLException(SQLException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error("数据库异常");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public R handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error("数据库异常");
    }
}
