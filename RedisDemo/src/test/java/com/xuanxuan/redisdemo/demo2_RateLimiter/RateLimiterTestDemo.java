package com.xuanxuan.redisdemo.demo2_RateLimiter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RateLimiterTestDemo {

    @Resource
    private RedisLimiterManager redisLimiterManager;

    @Test
    public void test() throws InterruptedException {
        String testUserId = "userId:1";

        // 模拟同时发请求，这边肯定在一秒之内执行完
        // 理想情况：vip 用户可以全执行完
        for (int i = 0; i < 5; i ++) {
            redisLimiterManager.doRateLimiter(testUserId, "vip");
            System.out.println("Vip Success " + i + " !");
        }

        // 重新获取令牌
        Thread.sleep(2000);

        // 模拟同时发请求，这边肯定在一秒之内执行完
        // 理想情况：普通用户只执行 1 次就抛异常了
        for (int i = 0; i < 5; i ++) {
            redisLimiterManager.doRateLimiter(testUserId, "member");
            System.out.println("Member Success " + i + " !");
        }
    }
}
