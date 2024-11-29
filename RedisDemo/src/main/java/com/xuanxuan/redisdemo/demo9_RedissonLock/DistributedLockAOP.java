package com.xuanxuan.redisdemo.demo9_RedissonLock;

import com.xuanxuan.redisdemo.annotation.DistributedLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class DistributedLockAOP {

    @Resource
    private RedissonClient redissonClient;

    @Around("@annotation(distributedLock))")
    public Object getLock(ProceedingJoinPoint proceedingJoinPoint, DistributedLock distributedLock) {
        String lockKey = distributedLock.key();
        long releaseTime = distributedLock.releaseTime();
        long waitTime = distributedLock.waitTime();
        TimeUnit timeUnit = distributedLock.timeUnit();

        // 1) 创建锁
        RLock lock = redissonClient.getLock(lockKey);

        try {
            // 2) 尝试加锁
            boolean success = lock.tryLock(waitTime, releaseTime, timeUnit);

            if (success) {
                return proceedingJoinPoint.proceed();
            } else {
                throw new RuntimeException("获取锁失败: " + lockKey);
            }

        } catch (Throwable e) {
            throw new RuntimeException(e);
        } finally {
            if (lock != null && lock.isLocked())
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                    System.out.println("thread: " + Thread.currentThread().getName() + " unlock: " + lockKey);
                }
        }
    }
}
