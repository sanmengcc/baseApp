package com.base.app.config.encrypt;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
public class NeedSign {

    @Value("${api.sign.open}")
    private boolean sign;

    @Value("${api.sign.signKey}")
    private String signKey;

    @Value("${api.sign.signStr}")
    private String signStr;

    public String getSignKey(){
        return signKey;
    }

    public String getSignStr() {
        return signStr;
    }

    public boolean getSign() {
        return sign;
    }
}
