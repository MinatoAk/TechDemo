package com.xuanxuan.demos.rabbitmq.demo6_businessMQ;

import cn.hutool.core.util.StrUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class MyMessageConsumer {

    /**
     * 消费者应该是实际处理任务的角色，类似于线程池的线程
     *
     * 消费消息，并且手动确认消息，一般实际项目都是手动，也就是配置 autoack: false
     *
     * @param message
     * @param channel: 用与和 RabbitMQ 通信的频道，需要用 channel 的方法手动拒绝或接受
     * @param deliveryTag: 指定接收或拒绝的是哪一条消息
     */
    @RabbitListener(queues = {MyMQConstant.BUSINESS_QUEUE}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        // 1. 记录当前收到的消息
        log.info("Receive Message = {}", message);


        // 2. 消息如果为空，直接抛 NACK
        if (StrUtil.isBlank(message)) {
            channel.basicNack(deliveryTag, false, false);
            throw new RuntimeException("消息为空！");
        }


        // 3. 从消息里拿到对象id，并查数据库取出对象，如果对象为空也抛 NACK
        long objectId = Long.parseLong(message);


        // 4. 具体的执行任务逻辑


        // 5. 执行任务成功，返回 ACK
        channel.basicAck(deliveryTag, false);
    }
}
