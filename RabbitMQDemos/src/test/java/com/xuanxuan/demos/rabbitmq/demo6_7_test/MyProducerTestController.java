package com.xuanxuan.demos.rabbitmq.demo6_7_test;

import cn.hutool.core.util.IdUtil;
import com.xuanxuan.demos.rabbitmq.demo6_7_businessMQ.MyMessageProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 模拟一个 Controller，提交任务到消息队列
 */
@SpringBootTest
public class MyProducerTestController {

    @Resource
    private MyMessageProducer myMessageProducer;

    @Test
    public void handleObject() {
        // 传递执行任务，也就是消费者需要的参数就行了
        // 如果要传多个参数直接参考 demo5 传一个 HashMap，配合 JSONUtil 就可以取了
        long objectId = 1234;
        myMessageProducer.sendMessage(String.valueOf(objectId));
    }

    @Test
    public void handleObjectWithTTL() {
        long objectId = 1234;
        long TTL = 60000;
        myMessageProducer.sendMessageWithTTL(String.valueOf(objectId), TTL);
    }

    @Test
    public void handleObjectWithId() {
        String messageId = IdUtil.randomUUID();
        long objectId = 1234;
        myMessageProducer.sendMessageWithId(String.valueOf(objectId), messageId);
    }

}
