package com.base.core.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JacksonAnnotationsInside
public @interface ApiPermission {

    /**
     * 是否验证权限
     * @return
     */
    boolean auth() default true;

    /**
     * 是否验证登陆
     * @return
     */
    boolean login() default true;
}
