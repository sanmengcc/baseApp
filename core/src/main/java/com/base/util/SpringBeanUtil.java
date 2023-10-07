package com.base.util;

import com.base.core.constant.SystemConstants;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Objects;

/**
 * 获取Spring bean
 */
@Component
public class SpringBeanUtil implements ApplicationContextAware {

    // Spring应用上下文
    private static ApplicationContext applicationContext;

    /**
     * 根据名称获取bean
     *
     * @param name bean名称
     * @return bean实例
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据类型获取bean
     *
     * @param clazz bean的Class类型
     * @return bean实例
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> Map<String, T> getBeansByType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 根据注解类型获取所有标注此类型注解的bean实例
     *
     * @param annoClazz 注解类型
     * @return bean实例
     */
    public static <T extends Annotation> Map<String, Object> getBeansWithAnnotation(Class<T> annoClazz) {
        return applicationContext.getBeansWithAnnotation(annoClazz);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return SpringBeanUtil.applicationContext;
    }


    /**
     * 获取当前的环境参数
     *
     * @return
     */
    public static String getProfile() {
        String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        if (Objects.nonNull(profiles) && profiles.length > 0) {
            return profiles[0];
        }
        return SystemConstants.Profile.DEV;
    }

}
