package com.base.app.service.impl;

import com.base.app.constants.CacheConstants;
import com.base.app.service.CaptchaService;
import com.base.app.service.RedisService;
import com.base.util.UUIDGenerator;
import com.google.code.kaptcha.Producer;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.FastByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class CaptchaServiceImpl implements CaptchaService {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;
    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;
    @Resource
    private RedisService redisService;

    /**
     * 验证码类型
     */
    private String captchaType = "math";

    @Override
    public Map<String, String> getCaptchaImage() {
        String capStr = null, code = null;
        BufferedImage image = null;
        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }
        // 保存验证码信息
        String captchaId = UUIDGenerator.generate();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + captchaId;
        log.error("保存验证码 KEY: " + verifyKey + " VALUE：" + code );
        redisService.setValue(verifyKey, code, CacheConstants.CAPTCHA_EXPIRATION);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException ex) {
            log.error("ImageIO.write:{}", ex);
        }
        Map<String, String> map = new HashMap<>();
        map.put("captchaId", captchaId);
        map.put("img", Base64Utils.encodeToString(os.toByteArray()));
        return map;
    }

    @Override
    public boolean validateCaptchaImage(String captchaId, String text) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + captchaId;
        Object redisValue = redisService.getValue(verifyKey);
        if (Objects.nonNull(redisValue)) {
            return redisValue.toString().equals(text);
        }
        return false;
    }
}
