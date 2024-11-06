package com.xuanxuan.redisdemo.demo2_RateLimiter;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisLimiterManager {

    @Resource
    private RedissonClient redissonClient;

    /**
     * 执行限流操作
     * @param limiterKey: 如果对用户限流，我们的 key 选择用 userId，不同用户对应不同的限流器
     */
    public void doRateLimiter(String limiterKey, Boolean isVip) {
        // 1. 创建一个限流器，默认是用令牌桶算法，每秒钟发放 5 个令牌
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(limiterKey);
        rateLimiter.trySetRate(RateType.OVERALL, 5, 1, RateIntervalUnit.SECONDS);

        // 2. 尝试获取令牌，如果是普通用户每秒允许访问1次，vip用户每秒允许访问5次
        boolean success = false;
        if (isVip) {
            success = rateLimiter.tryAcquire(1);
        } else {
            success = rateLimiter.tryAcquire(5);
        }

        // 3. 如果获取失败则抛异常
        if (!success) {
            throw new RuntimeException("访问太频繁了，请稍后再试！");
        }
    }
}
