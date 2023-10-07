package com.base.app.config.encrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.LinkedHashMap;

@Slf4j
@Configuration
public class SignUtil {

    @Resource
    private NeedSign needSign;

    /**
     * 签名生成逻辑
     * 注意去除  [ ] " 三个特殊字符，否则前后端由于数据类型会造成签名异常
     */
    public String createSign(LinkedHashMap params) throws Exception {
        StringBuilder ketStr = new StringBuilder();
        Object[] keys = params.keySet().toArray();
        Arrays.sort(keys);
        LinkedHashMap newmap = new LinkedHashMap();
        for (Object key : keys) {
            if (!key.toString().equals(needSign.getSignKey())) {
                newmap.put(key, params.get(key));
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        ketStr.append(mapper.writeValueAsString(newmap));
        String str = ketStr.append(needSign.getSignStr()).toString();
        str = str.replaceAll("\\[", "");
        str = str.replaceAll("]", "");
        str = str.replaceAll("\"", "");
        return  md5(str);
    }

    public String md5(String str) throws Exception{
        // 指定加密类型
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // 将字节数组转换为表示每个字节的十六进制值的字符串
        return Hex.encodeHexString(messageDigest.digest(str.getBytes(StandardCharsets.UTF_8)));
    }
}
