package com.xuanxuan.demos.rabbitmq.demo3_Exchange;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

public class DirectConsumers {

    private final String DIRECT_QUEUE1 = "demo3.direct.queue1";
    private final String DIRECT_QUEUE2 = "demo3.direct.queue2";

    @RabbitListener(queues = DIRECT_QUEUE1)
    public void consumeFanoutQueue1(String msg) {
        System.out.println("消费队列 1 收到消息:[" + msg +"]");
    }

    @RabbitListener(queues = DIRECT_QUEUE2)
    public void consumeFanoutQueue2(String msg) {
        System.out.println("消费队列 2 收到消息:[" + msg + "]");
    }
}
