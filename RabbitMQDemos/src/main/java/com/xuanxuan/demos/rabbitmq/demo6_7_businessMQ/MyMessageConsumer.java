package com.xuanxuan.demos.rabbitmq.demo6_7_businessMQ;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

        // 2. 消息如果为空，直接抛 Reject
        if (StrUtil.isBlank(message)) {
            channel.basicReject(deliveryTag, false);
            throw new RuntimeException("消息为空！");
        }

        // 3. 取出必要的参数
        // todo: 参数需要校验，如果有为空的直接 Reject，因为是参数问题
        HashMap<String, Object> map = JSONUtil.toBean(message, HashMap.class);

        String cacheKey = map.get("cacheKey").toString();
        String appJson = map.get("app").toString();
        String choicesJson = map.get("choices").toString();

        App app = JSONUtil.toBean(appJson, App.class);
        List<String> choices = JSONUtil.toList(choicesJson, String.class);

        System.out.println("Params are shown as follows:");
        System.out.println("cacheKey = " + cacheKey);
        System.out.println("app = " + app);
        System.out.println("choices = " + choices);

        // 4. 修改当前任务在执行中
        updateTaskStatusRunning();

        // 5. 具体的执行任务逻辑


        // 5.x 任务执行成功，更新任务状态成功
        updateTaskStatusSuccess();

        // 6. 执行任务成功，返回 ACK
        channel.basicAck(deliveryTag, false);
    }


//    /**
//     * 测试拿到消息的 id
//     *
//     * @param message
//     * @param channel
//     * @param deliveryTag
//     */
//    @RabbitListener(queues = {MyMQConstant.BUSINESS_QUEUE}, ackMode = "MANUAL")
//    public void receiveMessage(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
//        // 1. 记录当前收到的消息
//        String payLoad = new String(message.getBody());
//        log.info("Receive Message with ID = {} and Payload = {}", message.getMessageProperties().getMessageId(), payLoad);
//
//        // 2. 消息如果为空，直接抛 NACK
//        if (StrUtil.isBlank(payLoad)) {
//            channel.basicNack(deliveryTag, false, false);
//            throw new RuntimeException("消息为空！");
//        }
//
//
//        // 3. 从消息里拿到对象id，并查数据库取出对象，如果对象为空也抛 NACK
//        long objectId = Long.parseLong(payLoad);
//        System.out.println(objectId);
//
//
//        // 4. 具体的执行任务逻辑
//
//
//        // 5. 执行任务成功，返回 ACK
//        channel.basicAck(deliveryTag, false);
//    }

    private void updateTaskStatusSuccess() {
        System.out.println("[x] 修改数据库任务状态字段为成功: success");
    }
    private void updateTaskStatusRunning() {
        System.out.println("[x] 修改数据库任务状态字段执行中: running");
    }
}
