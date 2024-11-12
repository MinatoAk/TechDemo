package com.xuanxuan.redisdemo.demo4_preCacheJob;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class preCacheJob {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private RedissonClient redissonClient;

    private List<Long> hotDataIds = Arrays.asList(1L, 2L, 3L);

    private final String redissonKey = "scheduled:preCache:lock";

    /**
     * 定时做缓存预热，只缓存热点数据
     * 指定定时任务时间的语法直接网上查
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void preCacheJob() throws InterruptedException {
        // 1. 拿到分布式锁才能去重建缓存
        RLock lock = redissonClient.getLock(redissonKey);

        try {
            // 2. 开看门狗机制自动续期
            boolean success = lock.tryLock(10, -1, TimeUnit.SECONDS);

            if (!success) return;

            for (Long id : hotDataIds) {
                // 1. 根据 id 拿到热点数据对应的实体对象

                // 2. 缓存预热到数据库
                String redisKey = String.format("system:user:%d", id);
                redisTemplate.opsForValue().set(redisKey, "userEntity", 30, TimeUnit.MINUTES);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (lock != null && lock.isLocked())
                if (lock.isHeldByCurrentThread())
                    lock.unlock();
        }

    }
}
