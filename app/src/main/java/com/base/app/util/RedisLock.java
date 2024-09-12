package com.base.app.util;

import com.base.app.service.core.RedisService;
import com.base.util.SpringBeanUtil;
/**
 * redis 分布式锁
 */
public class RedisLock {

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    private final String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 120 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 20 * 1000;

    private volatile Boolean locked = false;

    private String myExpires = "";

    public RedisLock(final String lockKey) {
        this.lockKey = lockKey + "_lock";
    }

    public RedisLock(final String lockKey, final int timeoutMsecs) {
        this(lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    public RedisLock(final String lockKey, final int timeoutMsecs, final int expireMsecs) {
        this(lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    public String getLockKey() {
        return lockKey;
    }

    public synchronized Boolean lock() throws InterruptedException {
        RedisService redisService = SpringBeanUtil.getBean(RedisService.class);

        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            final long expires = System.currentTimeMillis() + expireMsecs + 1;
            //锁到期时间
            final String expiresStr = String.valueOf(expires);
            if (redisService.setNx(lockKey, expiresStr)) {
                myExpires = expiresStr;
                locked = true;
                return true;
            }

            final Long currentValueStr = redisService.getExpire(lockKey);
            if (currentValueStr != null && currentValueStr < System.currentTimeMillis()) {
                //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
                final String oldValueStr = redisService.getSet(lockKey, expiresStr);
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受
                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    myExpires = expiresStr;
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
        }
        return false;
    }

    public synchronized void unlock() {
        RedisService redisService = SpringBeanUtil.getBean(RedisService.class);
        // 如果当前redis中的锁与上锁相同删除锁
        if (myExpires.equals(redisService.getExpire(lockKey))) {
            if (locked) {
                redisService.delete(lockKey);
                locked = false;
            }
        }
    }
}