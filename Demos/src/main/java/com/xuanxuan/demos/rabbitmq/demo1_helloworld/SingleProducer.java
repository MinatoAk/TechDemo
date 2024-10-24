package com.xuanxuan.demos.rabbitmq.demo1_helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

public class SingleProducer {

    /**
     * 向名为 hello 的队列发送消息
     */
    private final static String QUEUE_NAME = "demo1_queue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        /**
         * 和 RabbitMQ 服务建立连接
         */
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // 频道可以看作是操作 RabbitMQ 消息队列的客户端
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Test message 1";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
