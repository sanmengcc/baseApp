package com.base.app.util;

import com.base.core.exception.CloudException;
import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 指定缓存失效时间
     * @param key
     * @param time 单位 秒
     */
    public void expire(Serializable key, long time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     * @param key 键
     * @param value 值
     * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Serializable value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置Nx
     * @param key
     * @param value
     * @return
     */
    public Boolean setNx(final String key, final String value) {
        Boolean b = false;
        try {
            b = (Boolean) redisTemplate.execute((final RedisConnection c) -> {
                final StringRedisSerializer serializer = new StringRedisSerializer();
                final Boolean success = c.setNX(serializer.serialize(key), serializer.serialize(value));
                c.close();
                return success;
            });
        } catch (Exception e) {
            return false;
        }
        return b;
    }

    /**
     * getExpire
     * @param key
     * @return
     */
    public String getExpireString(final String key) {
        String obj = null;
        try {
            obj = (String) redisTemplate.execute((final RedisConnection c) -> {
                final StringRedisSerializer serializer = new StringRedisSerializer();
                final byte[] data = c.get(serializer.serialize(key));
                c.close();
                return serializer.deserialize(data);
            });
        } catch (Exception ex) {
            throw new CloudException("getExpire Fail.");
        }
        return obj;
    }

    /**
     * getSet
     * @param key
     * @param value
     * @return
     */
    public String getSet(final String key, final String value) {
        String obj = null;
        try {
            obj = (String) redisTemplate.execute((final RedisConnection c) -> {
                final StringRedisSerializer serializer = new StringRedisSerializer();
                final byte[] ret = c.getSet(serializer.serialize(key), serializer.serialize(value));
                c.close();
                return serializer.deserialize(ret);
            });
        } catch (Exception ex) {
            throw new CloudException("getSet Fail.");
        }
        return obj;
    }
}
