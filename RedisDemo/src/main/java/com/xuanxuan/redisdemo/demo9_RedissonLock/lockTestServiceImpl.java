package com.xuanxuan.redisdemo.demo9_RedissonLock;

import com.xuanxuan.redisdemo.annotation.DistributedLock;
import org.springframework.stereotype.Service;

@Service
public class lockTestServiceImpl implements lockTestService {

    private final String lockKey = "test:annotation:lock";

    @Override
    @DistributedLock(key = lockKey, waitTime = 1000)
    public void testLockKey() {
        System.out.println("Lock Key Success!");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
