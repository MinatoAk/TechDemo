package com.xuanxuan.demos.rabbitmq.demo6_test;

import com.xuanxuan.demos.rabbitmq.demo6_businessMQ.MyMessageProducer;
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

}
