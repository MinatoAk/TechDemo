package com.xuanxuan.concurrentdemos.demo4_commitBatchTasks;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class batchTasksTest {

    // 自定义 IO 密集型线程池
    private ThreadPoolExecutor customThreadPoolExecutor = new ThreadPoolExecutor(
            4,
            10,
            60L,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new ThreadPoolExecutor.AbortPolicy()
    );

    private void dealWithData(List<Integer> dataList) {
        int sum = dataList.stream()
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println(sum);
    }

    @Test
    public void test() {
        // 保存所有 completableFutures
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 示例需要处理的数据集合
        List<Integer> dataList = new ArrayList<>();
        for (int i = 0; i < 1000; i ++ ) dataList.add(i);

        int batchSize = 100;
        int totalSize = dataList.size();
        for (int i = 0; i < totalSize; i += batchSize) {
            // 当前要处理的数据批
            List<Integer> currentList = dataList.subList(i, Math.min(i + batchSize, totalSize));

            // 处理当前批次数据
            CompletableFuture.runAsync(() -> {
                System.out.println("[x]" + Thread.currentThread().getName() + " 开始处理数据");
                dealWithData(currentList);

            }, customThreadPoolExecutor).exceptionally(ex -> {
                // 实际应该打印日志
                System.out.println("处理数据出错：" + ex.getMessage());
                return null;
            });
        }

        // 等待所有任务处理完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 关闭线程池
        customThreadPoolExecutor.shutdown();
    }

}
