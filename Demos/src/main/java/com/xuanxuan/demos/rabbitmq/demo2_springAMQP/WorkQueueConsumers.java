package com.xuanxuan.demos.rabbitmq.demo2_springAMQP;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WorkQueueConsumers {

    private final String WORK_QUEUE = "work_queue";

    @RabbitListener(queues = WORK_QUEUE)
    public void workQueueConsumer1(String message) throws InterruptedException {
        System.out.println("workQueueConsumer1: [" + message + "]");
        Thread.sleep(20);
    }

    @RabbitListener(queues = WORK_QUEUE)
    public void workQueueConsumer2(String message) throws InterruptedException {
        System.err.println("workQueueConsumer2: [[[[" + message + "]]]]");
        Thread.sleep(50);
    }
}
