package com.xuanxuan.springdemo.demo4_Scheduler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SchedulerTest {

    @Test
    public void testTimer() throws InterruptedException {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println("任务执行中...");
            }
        };

        timer.schedule(task, 1000, 2000);

        Thread.sleep(15000);
    }

    @Test
    public void testScheduledExecutorService() throws InterruptedException {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> System.out.println("任务执行中..."),
        1, 2, TimeUnit.SECONDS);

        Thread.sleep(15000);
    }
}
