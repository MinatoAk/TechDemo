# 轩轩的中间件学习 Demo

## Summary

记录了轩轩学习各种后端中间件的过程用到的 Demo，希望对你有帮助！

在启动对应的 demo 时请您**务必阅读对应栏目下的食用指南**，部分 demo 代码可能仅在 test 中存在。

有任何的问题，欢迎您联系我或者提出 issue，我会及时进行更新  :)

</br>

正在进行 On Going:

- Redis
- Concurrent Programming
- RabbitMQ
- Elasticsearch
- Spring
- DesignPattern

</br>

目前完成 Done:



</br>

即将到来 Coming Soon:

</br>

</br>

## Redis

### 食用指南

在使用前请您先根据自己的配置来改写对应的 yml 文件，保证 Redis 可用，并且更新 Maven 中的相关依赖。



### demo 指南

- demo1: 互斥锁解决缓存击穿问题，在本 demo 中，使用 Caffeine 本地缓存来缓存对象，并且使用 Redisson 分布式锁来解决缓存击穿问题。在 config 中配置了 Redisson 客户端，在 constant 中指明了相关的 `<lock_key>`，请您在自己写代码时也遵循相关规范；
- demo2: 使用 Redisson 内置库实现令牌桶限流方法，区分普通用户和会员用户，封装了通用的方法，开箱即用；
- demo3: 自己配置 `redisTemplate` 实现缓存的基本增删查改，也可以使用 `stringRedisTemplate` 但是只支持存 value 为 `String` 类型，增加了可读性，自己配置的可以存多类型对象，但是牺牲了可读性；
- demo4: 用定时任务做缓存预热，使用分布式锁保证只有一台机器执行；
- demo5: 使用 `ZSet` 实现了实时排行榜功能；
- demo6: Redis 保证缓存一致性，更改数据库之后的删除缓存部分的实现，利用 `SCAN` 命令，删除所有特定前缀的 keys；
- demo7: Caffeine 保存缓存一致性，通过 `asMap` 拿到对应的 `ConcurrentHashMap` 之后遍历找到特定前缀的 keys 并且删除；
- demo8: 使用 `BitMap` 实现用户签到及查询签到情况；

</br>

</br>

## Concurrent Programming

### demo 指南

- demo1: 在 config 中自定义线程池并注册为单例 Bean，在 test 中演示如何提交任务到线程池以及如何监控线程池状态；
- demo2: 展示了几种控制线程执行顺序的方式，包括 `join()`，`ReentrantLock + Condition` ，其中包含使用多线程连续顺序打印 `ABC` 字符的 demo；
- demo3: 给出 `CompletableFuture` 的几种使用场景和具体使用方式；
- demo4: 提交批处理任务到线程池并发执行的通用模式；
- demo5: 字节算法题: 三个线程连续打印123，和 demo2 是不同的解法，本解法更好理解；
- demo6: 美团算法题: 手撕生产者消费者模型；

</br>

</br>

## RabbitMQ

### 食用指南

为了使您立刻能够上手，并且轻松成功编译项目，Producer 统一在测试文件下进行消息的发送，Consumers 在使用前需要加上 `@Component` 保证被当做 Bean 实例管理才能使用。但是需要您在本地装上 RabbitMQ 中间件，并且声明了对应 demo 下的 Consumer 监听的队列才能正常使用，否则会报错，请务必注意！

> 例: 假如您想体验一下 demo3_Exchange 中定向交换机的使用，需要先在 `DirectConsumers.java` 打上 `@Component` 注解，另外保证以任何方式(代码或 RabbitMQ 控制页面)创建出对应的交换机和监听的队列，并且指明 `BindingKey`，任何交换机或队列或`RoutingKey, Binding` 的名字可以按喜好进行更改或测试，但务必保证名称一致，我们习惯以 `.` 进行命名分隔，就像 Redis 习惯用 `:` 一样。



### demo 指南

- demo1: RabbitMQ 官方文档中第一个 hello world 的教程示例，是一个基本的单对单模式(Single Queue);

- demo2: 使用 Spring AMQP 依赖中的 RabbitTemplate 对 MQ 进行操作，其中包括了单对单模式(Single Queue)，工作队列模式(Work Queue)，而且未使用到交换机，直接向队列发送消息; 

- demo3: 使用三种交换机进行消息的路由，分别包括 Fanout, Direct, Topic 交换机;

- demo4: 使用代码来声明新的交换机，队列，绑定方式，基于 `@RabbitListener` 注解;

- demo5: 测试了如何收发对象到消息队列，消费者的方法入参类型一定要和生产者发进去的类型一致，另外我的习惯是用 `HuTool` 工具库转成 `JsonStr` 再发到队列里，这样一直收到的就是 `String` 类型，消费者再调个 `toBean()` 就可以了;

  > 使用 `JsonStr` 可以传的内容更少，默认传对象会调用消息转换器，使用 JDK 序列化，大小会变大；
  
- demo6: 在实际项目中使用 MQ 的整个流程，首先通过 MQInitMain 在项目启动之前先把交换机和队列创建出来；之后封装一个 Producer 提供通用的发消息方法，并且注册为 Bean；在 Controller 中通过这个 Bean 去发消息，发的消息只需要包含消费者需要的各种参数或者对象，具体可以参考 demo5 发一个 HashMap；最后在 Consumer 中接收消息并且进行对应的处理，这里需要注意，我们一般是不会启用 `autoack` 自动确认，而是手动通过 `Channel` 确认消息消费成功或消费失败，并且每一条消息最好都用日志记录下来，执行成功则调用 `basicACK`，失败则调用 `basicNACK`，并且按照业务需求选择是否重新入队；

  > 更新 demo6 版本: 补充了我自己把项目从同步改成异步的过程中真实的例子的简化版本；

- demo7: 合并在 demo6 中，展示了如何给消息添加 TTL 和 messageId，有了 messageId 就可以保证消息的幂等性；

- demo8: 在 config 中注册 RabbitAdmin，用于监控队列和交换机等信息，实现 RabbitService 提供获取某个队列当前任务数的方法；

</br>

</br>

## Elasticsearch

### 食用指南

在使用前，请先保证安装了 ES, Kibana，并安装了中文友好的分词插件 IK 分词器，另外请在配置文件中进行相关配置的更改。在运行该项目的任何 demo 之前，请您保证已经启动了 ES！



### demo 指南

- demo1: 展示了 Spring 中和 ES 交互(进行 CRUD)的两种方式 `ESDAO` 和 `ESRestTemplate`；

  > 第 1 种方式: `ElasticsearchRepository<PostEsDTO, Long>` 提供的默认方法，也可以按照属性自定义，不用自己实现逻辑，这种方式非常简单，能使用 `ESDAO` 的全部使用 `ESDAO` 完成；
  >
  > 具体实现: 先定义 DTO => 再定义 DAO => 最后直接注入 `ESDAO` 使用；
  >
  > 第 2 种方式: `ElasticsearchRestTemplate` 实现复杂的查询过程，类似与 `queryWrapper` 的构造；
  >
  > 具体实现: 先注入 `ESRestTemplate` => 再构造 `Query` 对象 => 调用 `ESRestTemplate` 的方法操作，传入 `Query` 和索引；
  
- demo2: 提到 ElasticStack 当然就避不开 Elk 日志收集系统，该 demo 演示了如何把日志输入到指定路径中的文件，在 `resources/logback-spring.xml` 下有相关的配置，配置相关流程在 md 文件中可见；

  整个 Elk 的流程如下: FileBeat 订阅日志 => LogStash 解析过滤日志，得到 Json 格式数据 => ES 存储日志 => Kibana 展示；

  注意: 请务必务必保证下载的 FileBeat, LogStash, Kibana 的版本完全和 ES 的一致，否则可能有问题！
  
- demo3: 全量同步和增量同步数据库中的数据到 ES 中的方式；

- demo4: 通过 ES 客户端验证 ES 正常连接的方法，如果 ES 未连接可以使用数据降级请求数据库，保证业务正常运行；

</br>

</br>

## Spring

### demo 指南

- demo1: 使用 Spring MVC 拦截器进行性能监控，计算请求的处理时间；
- demo2: 实现全局异常处理器，搭配自定义异常和状态码枚举类使用，另外还实现抛异常工具类，掌握了这个 demo 就明白了实际项目处理异常的流程；
- demo3: AOP 实现权限校验功能；
- demo4: 除了 `@Schedule` 注解之外的实现定时任务的两种方式: `Timer` 和 `ScheduledExecutorService`；
- demo5: 在配置文件中加上 `server.shutdown=graceful` 可以实现优雅停机，保证正在处理的所有请求执行完成之后再停机，另外还可以在启动类的 `run()` 前加上 `addShutdownHook` 在 JVM 停机前自定义执行逻辑；

</br>

</br>

## Design Pattern

### demo 指南

- demo1: 单例模式的四种实现方式，包括饿汉式，懒汉式，双检锁，静态内部类；
- demo2: 基于自定义注解的方式实现策略模式，对外提供统一的执行器作为访问接口，执行器内部使用反射机制来动态获取注解属性值，再选择匹配的策略算法；

</br>

</br>