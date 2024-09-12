package com.base.app.service.core;

/**
 * redis内部接口
 */
public interface RedisService {

    /**
     * 设置缓存 指定时间
     */
    void setValue(String key, String value, Long expire);

    /**
     * 设置缓存 不指定时间
     */
    void setValue(String key, String value);

    /**
     * 设置指定key的失效时间
     */
    void expire(String key, Long expire);

    /**
     * 获取指定key的有效时间
     */
    Long getExpire(String key);

    /**
     * 返回指定key是否存在
     */
    boolean hasExist(String key);

    /**
     * 删除指定key
     */
    void delete(String... key);

    /**
     * 获取指定key的内容
     */
    Object getValue(String key);


    /**
     * set nx
     */
    Boolean setNx(final String key, final String value);

    /**
     * getExpireString
     * @param key
     * @return
     */
    String getExpireString(final String key);

    /**
     * getSet
     * @param key
     * @param value
     * @return
     */
    String getSet(final String key, final String value);
}
