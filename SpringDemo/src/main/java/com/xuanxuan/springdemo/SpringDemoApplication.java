package com.xuanxuan.springdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
public class SpringDemoApplication {

    public static void main(String[] args) {

        // 在 JVM 停止前会执行的逻辑
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            System.out.println("JVM shutdown !!");
//        }));

        SpringApplication.run(SpringDemoApplication.class, args);
    }

}
