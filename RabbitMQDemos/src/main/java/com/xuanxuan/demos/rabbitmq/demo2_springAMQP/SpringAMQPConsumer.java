package com.xuanxuan.demos.rabbitmq.demo2_springAMQP;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

public class SpringAMQPConsumer {

    private final String SPRING_AMQP_QUEUE = "spring.amqp.queue";

    @RabbitListener(queues = SPRING_AMQP_QUEUE)
    public void receiveMessage(String message) {
        System.out.println("Received message: [" + message + "]");
    }
}
