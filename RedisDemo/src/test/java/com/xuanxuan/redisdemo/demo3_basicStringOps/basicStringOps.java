package com.xuanxuan.redisdemo.demo3_basicStringOps;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class basicStringOps {

    /**
     * redisTemplate 有几种方式:
     * 1. redisTemplate 的序列化器是 JDK 原生的序列化器，会造成乱码问题
     * 2. stringRedisTemplate 则是使用 string 类型序列化器，但是只能存 string 类型的 value
     * 3. 自定义配置 redisTemplate
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test() {
        /**
         * 切记切记切记，一定一定一定要给缓存加上 TTL
         */
        redisTemplate.opsForValue().set("name", "xuanxuan");
        redisTemplate.opsForValue().set("age", 22, 20000, TimeUnit.MILLISECONDS);
        redisTemplate.opsForValue().setIfAbsent("lock_key", "user:1");

        System.out.println(redisTemplate.opsForValue().get("name"));
        System.out.println(redisTemplate.opsForValue().get("age"));

        redisTemplate.delete("name");
    }
}
