package com.xuanxuan.redisdemo.demo1_CacheBreakDown;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CacheBreakDownTestDemo {
    @Autowired
    private CacheBreakDownDemo cacheBreakDownDemo;

    @Test
    public void test() throws InterruptedException {
        String res = cacheBreakDownDemo.getStringWithCacheBreakDown();
        System.out.println(res);
    }
}
