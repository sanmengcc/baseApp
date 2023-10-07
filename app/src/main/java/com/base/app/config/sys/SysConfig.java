package com.base.app.config.sys;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class SysConfig {

    @Value("${sys.loginPasswordErrorCount}")
    private Long loginPasswordErrorCount;

    public Long getLoginPasswordErrorCount() {
        return loginPasswordErrorCount;
    }
}