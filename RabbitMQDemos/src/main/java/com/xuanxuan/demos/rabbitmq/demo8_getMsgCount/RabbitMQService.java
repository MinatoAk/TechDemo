package com.xuanxuan.demos.rabbitmq.demo8_getMsgCount;

import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RabbitMQService {

    @Resource
    private RabbitAdmin rabbitAdmin;

    public int getQueueMsgCount(String queueName) {
        QueueInformation queueInfo = rabbitAdmin.getQueueInfo(queueName);
        if (queueInfo == null) throw new RuntimeException("找不到对应队列!");
        return queueInfo.getMessageCount();
    }

}
