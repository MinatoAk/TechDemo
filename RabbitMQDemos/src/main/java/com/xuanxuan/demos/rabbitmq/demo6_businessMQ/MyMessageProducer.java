package com.xuanxuan.demos.rabbitmq.demo6_businessMQ;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        rabbitTemplate.convertAndSend(MyMQConstant.BUSINESS_EXCHANGE, MyMQConstant.BUSINESS_ROUTING_KEY, message);
    }
}
