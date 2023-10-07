package com.base.app.service.impl;

import com.base.app.service.RedisService;
import com.base.app.util.RedisUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisUtils redisUtils;


    @Override
    public void setValue(String key, String value, Long expire) {
        redisUtils.set(key, value, expire);
    }

    @Override
    public void setValue(String key, String value) {
        redisUtils.set(key, value);
    }

    @Override
    public void expire(String key, Long expire) {
        redisUtils.expire(key, expire);
    }

    @Override
    public Long getExpire(String key) {
      return redisUtils.getExpire(key);
    }

    @Override
    public boolean hasExist(String key) {
        return redisUtils.hasKey(key);
    }

    @Override
    public void delete(String... key) {
        redisUtils.del(key);
    }

    @Override
    public Object getValue(String key) {
        return redisUtils.get(key);
    }
}
