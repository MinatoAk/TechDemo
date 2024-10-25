package com.xuanxuan.demos.rabbitmq.demo5_sendAndGetObject;

import cn.hutool.json.JSONUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

public class ObjectConsumer {

    private final String OBJECT_QUEUE = "demo5.object.queue";

    @RabbitListener(queues = OBJECT_QUEUE)
    public void receiveMessage(String json) {
        HashMap<String, Object> mp = JSONUtil.toBean(json, HashMap.class);
        System.out.println("demo5.object.queue 收到消息啦: [" + mp + "]");
    }
}
