package com.xuanxuan.redisdemo.demo7_caffeine_consistency;

import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class CaffeineConsistencyDemo {

    private final String keyPreffix = "appId:";

    /**
     * 创建 Caffeine 本地缓存
     */
    private final Cache<String, Integer> cacheMap =
            Caffeine.newBuilder().initialCapacity(1024)
                    .expireAfterAccess(5L, TimeUnit.MINUTES)
                    .build();

    /**
     * 添加 Caffeine 的初始数据，并且删除以特定 key 开头的所有 keys
     */
    @Test
    public void test() {
        // 0) Cache Aside 传入需要删除的 keys 前缀
        String deleteKeyPrefix = "appId:5";

        // 1) 添加初始数据
        for (int i = 0; i < 10; i ++)
            cacheMap.put(keyPreffix + i + ":xxx", i);

        // 2) 遍历本地缓存获取所有的 key-value
        ConcurrentMap<String, Integer> mp = cacheMap.asMap();
        mp.forEach((key, value) -> {
            if (key.startsWith(deleteKeyPrefix)) cacheMap.invalidate(key);
            else System.out.println(key + " : " + value);
        });

        // 3) 删除完成之后检查删除状况
        long sz = cacheMap.estimatedSize();
        System.out.println("删除完成之后本地缓存元素个数为: " + sz);
    }

}
