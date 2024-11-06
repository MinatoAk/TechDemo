package com.xuanxuan.demos.rabbitmq.demo6_7_test;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.xuanxuan.demos.rabbitmq.demo6_7_businessMQ.App;
import com.xuanxuan.demos.rabbitmq.demo6_7_businessMQ.MyMessageProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 模拟一个 Controller，提交任务到消息队列
 */
@SpringBootTest
public class MyProducerTestController {

    @Resource
    private MyMessageProducer myMessageProducer;

    /**
     * 这个是我真实改进项目的时候写的扔到消息队列的内容
     * 演示一下到底如何发对象和取对象，非常非常重要！！
     *
     * 是一个 controller 中的 add 方法，本来业务非常复杂
     * 涉及到多级缓存，我这里直接做了个简化，重点关注 MQ 的部分
     */
    @Test
    public void addUserAnswer() {
        // 1. 修改数据库任务状态字段为执行中
        // 具体可以通过 new UserAnswer, setUserAnswer(id), service.updateById(userAnswer)
        updateTaskStatusRunning();

        // 2. 提交任务到消息队列，具体的参数由消费者的需要来决定
        App app = new App();
        app.setId(1);
        app.setAppName("EXAMPLE_APPNAME");

        List<String> choices = Arrays.asList("A", "B", "C", "D");

        HashMap<String, Object> msg = new HashMap<>();
        msg.put("cacheKey", "EXAMPLE_CACHEKEY");
        msg.put("app", app);
        msg.put("choices", choices);

        String msgJson = JSONUtil.toJsonStr(msg);
        myMessageProducer.sendMessage(msgJson);
    }

    @Test
    public void handleObject() {
        // 传递执行任务，也就是消费者需要的参数就行了
        // 如果要传多个参数直接参考 demo5 传一个 HashMap，配合 JSONUtil 就可以取了
        long objectId = 1234;
        myMessageProducer.sendMessage(String.valueOf(objectId));
    }

    @Test
    public void handleObjectWithTTL() {
        long objectId = 1234;
        long TTL = 60000;
        myMessageProducer.sendMessageWithTTL(String.valueOf(objectId), TTL);
    }

    @Test
    public void handleObjectWithId() {
        String messageId = IdUtil.randomUUID();
        long objectId = 1234;
        myMessageProducer.sendMessageWithId(String.valueOf(objectId), messageId);
    }

    private void updateTaskStatusRunning() {
        System.out.println("[x] 修改数据库任务状态字段为执行中: running");
    }
}
