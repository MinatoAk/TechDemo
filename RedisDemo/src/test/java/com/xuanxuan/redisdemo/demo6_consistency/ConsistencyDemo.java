package com.xuanxuan.redisdemo.demo6_consistency;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import javax.annotation.Resource;

@SpringBootTest
public class ConsistencyDemo {
    /**
     * 需求如下:
     * 当我们更新某个应用的题目时，要对应把缓存中该应用的答案结果清空删除
     */

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final String cacheKeyPrefix = "appId:5:";

    /**
     * 创建测试数据
     */
    @Test
    public void createDemoData() {
        for (Integer i = 0; i < 10; i ++ ) {
            stringRedisTemplate.opsForValue().set(cacheKeyPrefix + i, i.toString());
        }
    }

    /**
     * 使用 SCAN 命令获取匹配的 keys，这是一个增量迭代命令，通过游标 cursor 逐步返回匹配的 keys，不会阻塞
     * 使用 KEYS 命令会造成阻塞，绝对不要使用
     */
    @Test
    public void scanSet() {
        ScanOptions options = ScanOptions.scanOptions().match(cacheKeyPrefix + "*").build();

        try (Cursor<byte[]> cursor = stringRedisTemplate.getConnectionFactory().getConnection().scan(options)) {
            while (cursor.hasNext()) {
                String key = new String(cursor.next());
                String value = stringRedisTemplate.opsForValue().get(key);
                System.out.println("Key: " + key + " Value: " + value);

                stringRedisTemplate.delete(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
