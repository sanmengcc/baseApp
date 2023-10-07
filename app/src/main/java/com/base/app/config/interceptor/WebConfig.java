package com.base.app.config.interceptor;

import com.base.app.config.encrypt.RequestFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Slf4j
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Bean
    public RequestFilter getMyRequestWrapperFilter() {
        return new RequestFilter();
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(getMyRequestWrapperFilter());
        registration.addUrlPatterns("/*");
        registration.setName("RequestFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

}
