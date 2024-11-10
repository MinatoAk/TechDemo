package com.xuanxuan.concurrentdemos.demo3_CompletableFutureTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.concurrent.*;

@SpringBootTest
public class CompletableFutureTest {

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    /**
     * 用法1: 提交任务到线程池执行
     */
    @Test
    public void test1_commitTask2ThreadPool() {
        for (int i = 0; i < 4; i ++ ) {
            CompletableFuture.runAsync(() -> {

                System.out.println(Thread.currentThread().getName() + ": running!");

            }, threadPoolExecutor);
        }
    }

    /**
     * 用法2: 有返回值的异步任务，并异步获取返回值
     * 注意: 尽量避免使用返回值的任务，会阻塞主线程，要用也要加 TTL
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> res = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "Hello, This is the result";
        }, threadPoolExecutor);

        // 1. get(long timeout, TimeUnit unit)方法会阻塞当前线程，直到任务完成或超时
        try {
            System.out.println(res.get(1, TimeUnit.SECONDS));
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        // 2. get()方法会阻塞当前线程，直到任务完成
        // System.out.println(res.get());

        // 3. join()方法会阻塞当前线程，直到任务完成，但相对于 get 不会抛异常，代码更简洁
        System.out.println(res.join());
    }

    /**
     * 用法3: 使用任务完成之后的回调，有三种方法
     * (1) 消费任务不返回结果
     * (2) 任务完成后对返回结果进行转换
     * (3) 确定任务执行顺序(非并行)
     */
    @Test
    public void test3() throws ExecutionException, InterruptedException {
        // 1. 消费任务不返回结果
        CompletableFuture.supplyAsync(() -> "Hello, ", threadPoolExecutor)
                .thenAccept(res -> System.out.println(res + "Here is xuanxuan!"));

        // 2. 任务完成后对任务进行转换
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello, ", threadPoolExecutor)
                .thenApply(res -> res + "Here is xuanxuan Again!");
        System.out.println(future.get());

        // 3. 确定任务执行顺序
        CompletableFuture.runAsync(() -> System.out.print("Hello, "), threadPoolExecutor)
                .thenRun(() -> System.out.println("Here is xuanxuan Again Again!"));
    }

    /**
     * 用法4: 处理任务执行中的异常
     */
    @Test
    public void test4() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    if (true) throw new RuntimeException("Something wrong!");
                    return "Hello, This is the result";
                }, threadPoolExecutor)
                .handle((res, ex) -> {
                    return res == null ? "Default Return" : res;
                });
        String res = future.join();
        System.out.println(res);
    }

    /**
     * 用法5: 任务编排
     * (1) thenCombine 组合两个任务的返回值
     * (2) thenCompose 把一个任务的输出当做另一个任务的输入，决定任务的执行顺序(非并行)
     * (3) allOf 等所有任务都执行完成(并行)
     * (4) anyOf 等其中一个任务完成
     */
    @Test
    public void test5() {
        // 1. thenCombine 组合两个任务的返回值
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "Hello, ");
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> "Here is xuanxuan!");

        CompletableFuture<String> combinedFuture = future1.thenCombine(future2, (res1, res2) -> res1 + res2);

        System.out.println(combinedFuture.join());


        // 2. thenCompose 把一个任务的输出当做另一个任务的输入
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello, ")
                .thenCompose(res -> CompletableFuture.supplyAsync(() -> res + "Here is xuanxuan Again!"));
        System.out.println(future.join());


        // 3. allOf 等所有任务都执行完毕
        // 4. anyOf 等其中一个任务完成
        CompletableFuture<String> taskA = CompletableFuture.supplyAsync(() -> "TaskA is done.");
        CompletableFuture<String> taskB = CompletableFuture.supplyAsync(() -> "TaskB is done.");
        CompletableFuture<String> taskC = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "TaskC is done.";
        });

        CompletableFuture<Void> headTask = CompletableFuture.allOf(taskA, taskB, taskC);
        CompletableFuture<Object> anyTask = CompletableFuture.anyOf(taskA, taskB, taskC);

        System.out.println("One of TaskA, TaskB, TaskC is over!");
        System.out.println(anyTask.join());

        System.out.println("TaskA, TaskB, TaskC all over!");
    }
}
