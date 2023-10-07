package com.base.app.config.encrypt;


import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;

/**
 * 判断是否需要加解密
 */
@Configuration
@Log4j2
public class NeedCrypto {

    //开关变量，在配置文件中配置是否开启加解密功能，不需要可以去掉
    @Value("${api.encrypt}")
    private boolean encryptApiFlag;

    private static boolean flag = false;

    @PostConstruct
    public void NeedCrypto() {
        flag = encryptApiFlag;
    }

    /**
     * 是否需要对结果加密
     * 1.类上标注或者方法上标注,并且都为true
     * 2.有一个标注为false就不需要加密
     */
    public boolean needEncrypt(MethodParameter returnType) {
        return true;
    }

    /**
     * 是否需要参数解密
     * 1.类上标注或者方法上标注,并且都为true
     * 2.有一个标注为false就不需要解密
     */
    public boolean needDecrypt(MethodParameter parameter) {
        return true;
    }
}
