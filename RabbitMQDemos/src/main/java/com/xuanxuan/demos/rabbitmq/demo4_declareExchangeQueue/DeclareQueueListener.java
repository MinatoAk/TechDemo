package com.xuanxuan.demos.rabbitmq.demo4_declareExchangeQueue;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

public class DeclareQueueListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "new.queue1", durable = "true"),
            exchange = @Exchange(name = "new.exchange", type = ExchangeTypes.DIRECT),
            key = {"red", "yellow"}
    ))
    public void listenNewQueue1(String msg) {
        System.out.println("DeclareQueueListener 1: [" + msg + "]");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "new.queue2", durable = "true"),
            exchange = @Exchange(name = "new.exchange", type = ExchangeTypes.DIRECT),
            key = {"red", "blue"}
    ))
    public void listenNewQueue2(String msg) {
        System.out.println("DeclareQueueListener 2: [" + msg + "]");
    }
}
