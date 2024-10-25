# 轩轩的中间件学习 Demo

## Summary

记录了轩轩学习各种后端中间件的过程用到的 Demo，希望对你有帮助！

在启动对应的 demo 时请您**务必阅读对应栏目下的食用指南**！

</br>

正在进行 On Going:

- RabbitMQ

</br>

目前包括 Done:



</br>

即将到来 Coming Soon:

- Redis
- ElasticSearch
- 设计模式

</br>

</br>

## RabbitMQ

### 食用指南

为了使您立刻能够上手，并且轻松成功编译项目，对于 Producer 我们统一在测试文件下进行消息的发送，对于 Consumers 我们在使用前需要加上 `@Component` 保证被当做 Bean 实例管理才能使用，但是需要您在本地装上 RabbitMQ 中间件，并且声明了对应 demo 下的 Consumer 监听的队列才能正常使用，否则会报错，请您务必注意！

> 例: 假如您想体验一下 demo3_Exchange 中定向交换机的使用，您需要先在 `DirectConsumers.java` 打上 `@Component` 注解，另外保证您以任何方式(代码或 RabbitMQ 控制页面)创建出对应的交换机和监听的队列，并且指明 `BindingKey`，任何交换机或队列或`RoutingKey, Binding` 的名字可以按您的喜好进行更改或测试，但务必保证名称一致，我们习惯以 `.` 进行命名分隔，就像 Redis 习惯用 `:` 一样。



### demo 指南

- demo1: RabbitMQ 官方文档中第一个 hello world 的教程示例，是一个基本的单对单模式(Single Queue);

- demo2: 使用 Spring AMQP 依赖中的 RabbitTemplate 对 MQ 进行操作，其中包括了单对单模式(Single Queue)，工作队列模式(Work Queue)，而且未使用到交换机，直接向队列发送消息; 

- demo3: 使用三种交换机进行消息的路由，分别包括 Fanout, Direct, Topic 交换机;

- demo4: 使用代码来声明新的交换机，队列，绑定方式，基于 `@RabbitListener` 注解;

- demo5: 测试了如何收发对象到消息队列，消费者的方法入参类型一定要和生产者发进去的类型一致，另外我的习惯是用 `HuTool` 工具库转成 `JsonStr` 再发到队列里，这样一直收到的就是 `String` 类型，消费者再调个 `toBean()` 就可以了;

  > 使用 `JsonStr` 可以传的内容更少，默认传对象会调用消息转换器，使用 JDK 序列化，大小会变大;