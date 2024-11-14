package com.xuanxuan.elasticsearchdemos.demo2_ELK;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class LogTest {

    @Test
    public void test() {
        // debug 级别在 info 级别之下，我们配置的是 info 级别
        // 所以 debug 级别的日志不会输出
        log.info("Info Message");
        new Thread(() -> log.error("Error Message")).start();
        new Thread(() -> log.debug("Debug Message")).start();
        log.error("参数不正确，拒绝该消息，messageId:{}", 12345);
    }
}
