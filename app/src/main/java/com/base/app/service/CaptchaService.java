package com.base.app.service;


import java.util.Map;

/**
 * 验证码接口
 */
public interface CaptchaService {

    /**
     * 获取图片验证码
     * @return
     */
    Map<String, String> getCaptchaImage();

    /**
     * 验证图片验证码
     * @param captchaId
     * @param text
     * @return
     */
    boolean validateCaptchaImage(String captchaId, String text);

}
