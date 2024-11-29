package com.xuanxuan.redisdemo.demo9_RedissonLock;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RedissonLockTest {

    @Resource
    private lockTestService lockTestService;

    @Test
    public void testLock() throws InterruptedException {
        new Thread(() -> {
            lockTestService.testLockKey();
        }).start();

        // 会等待到最长等待时间，并且之后抛出获取失败的异常
        new Thread(() -> {
            lockTestService.testLockKey();
        }).start();

        Thread.sleep(15000);
    }

}
