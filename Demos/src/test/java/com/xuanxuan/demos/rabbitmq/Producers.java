package com.xuanxuan.demos.rabbitmq;


import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
public class Producers {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String SPRING_AMQP_QUEUE = "spring.amqp.queue";
    private final String WORK_QUEUE = "work.queue";
    private final String FANOUT_EXCHANGE = "demo3.fanout";
    private final String DIRECT_EXCHANGE = "demo3.direct";
    private final String TOPIC_EXCHANGE = "demo3.topic";
    private final String OBJECT_QUEUE = "demo5.object.queue";

    /**
     * demo2_SpringAMQPConsumer
     */
    @Test
    public void testSendMessage2Queue() {
        String msg = "Hello, SpringAMQP!";
        rabbitTemplate.convertAndSend(SPRING_AMQP_QUEUE, msg);
    }

    /**
     * demo2_WorkQueueConsumers
     */
    @Test
    public void sendMsg2WorkQueue() {
        for (int i = 1; i <= 50; i ++ ) {
            rabbitTemplate.convertAndSend(WORK_QUEUE, "msg" + i);
        }
    }

    /**
     * demo3_FanoutConsumers
     */
    @Test
    public void sendMsg2FanoutExchange() {
        String msg = "Hello, Everyone!";
        rabbitTemplate.convertAndSend(FANOUT_EXCHANGE, null, msg);
    }

    /**
     * demo3_DirectConsumers
     */
    @Test
    public void sendMsg2DirectExchange() {
        String red_msg = "Hello, red!";
        String yellow_msg = "Hello, yellow!";
        String blue_msg = "Hello, blue!";
        String pink_msg = "Hello, pink!";

        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, "red", red_msg);
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, "yellow", yellow_msg);
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, "blue", blue_msg);
        // 测试一下，如果没有该 BindingKey 会发生什么
        rabbitTemplate.convertAndSend(DIRECT_EXCHANGE, "pink", pink_msg);
    }

    /**
     * demo3_TopicConsumers
     */
    @Test
    public void sendMsg2TopicExchange() {
        String routingKey1 = "china.news";
        String routingKey2 = "japan.news";
        String routingKey3 = "china";
        String routingKey4 = "china.weather";
        String routingKey5 = "china.";

        String chinaNewsMsg = "Hello, china.news!";
        String japanNewsMsg = "Hello, japan.news!";
        String chinaMsg = "Hello, china!";
        String chinaWeatherMsg = "Hello, china.weather!";
        String chinaDotMsg = "Hello, china.!";

        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, routingKey1, chinaNewsMsg);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, routingKey2, japanNewsMsg);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, routingKey3, chinaMsg);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, routingKey4, chinaWeatherMsg);
        rabbitTemplate.convertAndSend(TOPIC_EXCHANGE, routingKey5, chinaDotMsg);
    }


    /**
     * demo5_ObjectConsumer
     */
    @Test
    public void sendObject2Queue() {
        HashMap<String, Object> mp = new HashMap<>();
        mp.put("name", "xuanxuan");
        mp.put("age", 22);

        String objectJson = JSONUtil.toJsonStr(mp);

        rabbitTemplate.convertAndSend(OBJECT_QUEUE, objectJson);
    }
}

