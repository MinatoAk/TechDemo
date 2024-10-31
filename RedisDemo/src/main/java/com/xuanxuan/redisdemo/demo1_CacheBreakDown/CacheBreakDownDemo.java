package com.xuanxuan.redisdemo.demo1_CacheBreakDown;

import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.xuanxuan.redisdemo.constant.KeyTTLConstant;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class CacheBreakDownDemo {
    /**
     * 缓存穿透问题的解决：
     * 场景：用 Caffeine 本地缓存缓存一个对象，如果该对象失效，则高并发请求打到 Redis，我们希望只有一个线程重建缓存。
     *      当然，也可以使用 Redis 来缓存对象。
     * 手段：使用 Redisson 分布式锁解决问题。
     */
    @Autowired
    private RedissonClient redissonClient;

    private final Cache<String, String> answerCacheMap =
            Caffeine.newBuilder().initialCapacity(1024)
                    .expireAfterAccess(5L, TimeUnit.MINUTES)
                    .build();

    private static final String CAFFEINE_KEY = "CAFFEINE_KEY";
    private static final String TARGET_STRING = "Hello, This is Cache Breakdown Demo!";

    public String getStringWithCacheBreakDown() throws InterruptedException {
        // 1. 根据 key 查询本地缓存
        String res = answerCacheMap.getIfPresent(CAFFEINE_KEY);

        // 2. 如果本地缓存存在对象，直接返回
        if (StrUtil.isNotBlank(res)) {
            return res;
        }

        // 3. 如果本地缓存不存在对象重建缓存
        RLock lock = redissonClient.getLock(KeyTTLConstant.LOCK_KEY);

        try {
            // 3.1 抢互斥锁
            // 抢锁过程最多等待 3s，拿到锁 15s 后超时释放
            boolean success = lock.tryLock(3, 15, TimeUnit.SECONDS);

            // 3.2 如果抢锁失败休眠 30s 后自旋重试
            while (!success) {
                Thread.sleep(30000);
                success = lock.tryLock(3, 15, TimeUnit.SECONDS);
            }

            // 3.3 进行 DOUBLE CHECK 防止是别的线程重建完的
            res = answerCacheMap.getIfPresent(CAFFEINE_KEY);
            if (StrUtil.isNotBlank(res)) {
                return res;
            }

            // 3.4 查询数据库拿到对象
            String targetString = TARGET_STRING;

            // 3.5 将对象放入本地缓存
            answerCacheMap.put(CAFFEINE_KEY, targetString);

            return targetString;
        } finally {
            // 4 释放锁
            if (lock != null && lock.isLocked()) {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
    }

}
