package com.base.app.constants;

/**
 * redis缓存常量
 */
public class CacheConstants {

    /**
     * 验证码
     */
    public static final String CAPTCHA_CODE_KEY = "CAPTCHA_CODE_KEY";

    /**
     * 验证码有效时间
     */
    public static final Long CAPTCHA_EXPIRATION = 60L;

    /**
     * 数据字典
     */
    public interface DictData{

        /**
         * 字典前缀
         */
        String PREX = "DICT:";

        /**
         * 缓存时间
         */
        Long TIME = 60 * 60 * 24 * 7L;
    }
}
