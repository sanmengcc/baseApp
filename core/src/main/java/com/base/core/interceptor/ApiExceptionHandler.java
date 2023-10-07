package com.base.core.interceptor;

import com.base.core.entity.R;
import com.base.core.exception.CloudException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * api 异常拦截
 */
@RestControllerAdvice("com.base")
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(BindException.class)
    public R handleBindException(BindException e) {
        return R.error(e.getFieldErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage());
        return R.error("服务器异常");
    }

    @ExceptionHandler(CloudException.class)
    public R handleCloudException(CloudException e) {
        log.error(e.getMessage());
        return R.error(e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    public R handleSQLException(SQLException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error("数据库异常");
    }

    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        log.error(e.getMessage());
        return R.error("数据库异常");
    }
}
