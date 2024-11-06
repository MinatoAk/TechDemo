package com.xuanxuan.demos.rabbitmq.demo6_7_businessMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MQInitMain {

    public static void main(String[] args) {

        /**
         * 程序启动前创建一次 demo 用到的交换机和队列
         */

        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(MyMQConstant.BUSINESS_EXCHANGE, "direct", true, false, null);

            // todo: 可以给队列声明 TTL DLX max-length
            channel.queueDeclare(MyMQConstant.BUSINESS_QUEUE, true, false, false, null);

            channel.queueBind(MyMQConstant.BUSINESS_QUEUE, MyMQConstant.BUSINESS_EXCHANGE, MyMQConstant.BUSINESS_BINDING_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
