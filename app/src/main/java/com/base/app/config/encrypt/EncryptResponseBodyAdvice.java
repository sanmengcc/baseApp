package com.base.app.config.encrypt;


import com.base.app.util.AESUtils;
import com.base.core.entity.R;
import com.base.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 请求响应处理类
 * 对加了@Encrypt的方法的数据进行加密操作
 */
@ControllerAdvice
@Slf4j
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        //在这里调用needEncrypet方法判断是否需要加密
        return new NeedCrypto().needEncrypt(returnType);
    }

    //这个方法截取了接口中返回的对象，在对对象加密后返回
    @Override
    public Object beforeBodyWrite(Object obj, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
        if (!(obj instanceof R)) {
            return obj;
        }
        R r = (R) obj;
        Object data = r.getData();
        if (Objects.nonNull(data)) {
            // 添加响应头
            serverHttpResponse.getHeaders().add("Encrypt", "true");
            String encryptData = "";
            if (isJsonable(data)) {
                encryptData = AESUtils.encrypt(toJson(data), "68dd2a6613ede433", "68dd2a6613ede433");
            } else {
                encryptData = AESUtils.encrypt(toJson(data), "68dd2a6613ede433", "68dd2a6613ede433");
            }
            r.setData(encryptData);
        }
        return r;
    }

    private String toJson(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isJsonable(Object obj) {
        try {
            new ObjectMapper().writeValueAsString(obj);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
