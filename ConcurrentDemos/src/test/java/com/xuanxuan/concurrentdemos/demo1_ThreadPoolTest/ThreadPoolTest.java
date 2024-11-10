package com.xuanxuan.concurrentdemos.demo1_ThreadPoolTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@SpringBootTest
public class ThreadPoolTest {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 利用 CompletableFuture 提交任务
     * runAsync 方法传入两个参数 Runnable 接口，自定义线程池
     */
    @Test
    public void testAddTask() throws InterruptedException {
        String taskName = "task-";

        /**
         * 这里如果提交超过4个任务就会触发拒绝策略，直接抛异常
         */
        for (int i = 0; i < 4; i ++ ) {
            String curTaskName = taskName + i;

            CompletableFuture.runAsync(() -> {
                System.out.println("任务执行中: " + curTaskName + " | 执行线程: " + Thread.currentThread().getName());
                // 以下是用来测试线程池执行原理的，明白线程池工作原理之后就知道下面是想干嘛了
//                try {
//                    Thread.sleep(4000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
            }, threadPoolExecutor);
        }

        Thread.sleep(2000);

        testGetThreadPoolInfo();
    }

    /**
     * 监控线程池信息
     */
    public void testGetThreadPoolInfo() {
        int sz = threadPoolExecutor.getQueue().size();
        System.out.println("队列长度: " + sz);

        long taskCount = threadPoolExecutor.getTaskCount();
        System.out.println("任务总数(无论成功或失败): " + taskCount);

        long completedTaskCount = threadPoolExecutor.getCompletedTaskCount();
        System.out.println("已成功完成任务数: " + completedTaskCount);

        long activeCount = threadPoolExecutor.getActiveCount();
        System.out.println("正在工作的线程数: " + activeCount);
    }
}
