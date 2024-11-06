package com.xuanxuan.demos.rabbitmq.demo6_7_businessMQ;

import cn.hutool.core.util.IdUtil;
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

    public void sendMessageWithTTL(String message, long TTL) {
        rabbitTemplate.convertAndSend(
                MyMQConstant.BUSINESS_EXCHANGE,
                MyMQConstant.BUSINESS_ROUTING_KEY,
                message,
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setExpiration(String.valueOf(TTL));
                    return messagePostProcessor;
                });
    }

    public void sendMessageWithId(String message, String messageId) {
        rabbitTemplate.convertAndSend(
                MyMQConstant.BUSINESS_EXCHANGE,
                MyMQConstant.BUSINESS_ROUTING_KEY,
                message,
                messagePostProcessor -> {
                    messagePostProcessor.getMessageProperties().setMessageId(messageId);
                    return messagePostProcessor;
                });
    }
}
