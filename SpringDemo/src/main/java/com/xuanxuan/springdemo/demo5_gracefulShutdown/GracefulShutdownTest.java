package com.xuanxuan.springdemo.demo5_gracefulShutdown;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GracefulShutdownTest {

    @GetMapping("/graceful/shutdown")
    public void gracefulShutdown() throws InterruptedException {
        System.out.println("gracefulShutdown api visited!");
        Thread.sleep(10000);
        System.out.println("shutdown after 10s, this request is completed!");
    }
}
