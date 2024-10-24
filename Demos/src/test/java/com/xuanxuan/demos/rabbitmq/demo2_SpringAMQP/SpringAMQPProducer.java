package com.xuanxuan.demos.rabbitmq.demo2_SpringAMQP;


import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringAMQPProducer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String SPRING_AMQP_QUEUE = "spring_amqp_queue";
    private final String WORK_QUEUE = "work_queue";

    @Test
    public void testSendMessage2Queue() {
        String msg = "Hello, SpringAMQP!";
        rabbitTemplate.convertAndSend(SPRING_AMQP_QUEUE, msg);
    }

    @Test
    public void sendMsg2WorkQueue() {
        for (int i = 1; i <= 50; i ++ ) {
            rabbitTemplate.convertAndSend(WORK_QUEUE, "msg" + i);
        }
    }
}
