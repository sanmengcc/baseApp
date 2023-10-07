package com.base.app.config.encrypt;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class TokenConfig {

    /**
     * token缓存前缀
     */
    @Value("${base.token.prex}")
    private String tokenPrex;

    /**
     * token缓存前缀
     */
    @Value("${base.token.prexUser}")
    private String tokenPrexUser;

    /**
     * token过期时间
     */
    @Value("${base.token.expires}")
    private Long tokenExpires;

    @Value("${base.token.prexPermission}")
    private String prexPermission;

    public String getPrexPermission() {
        return prexPermission;
    }
    public String getTokenPrex(){
        return tokenPrex;
    }

    public Long getExpires() {
        return tokenExpires;
    }

    public String getTokenPrexUser() {
        return tokenPrexUser;
    }

}
