package com.xuanxuan.concurrentdemos.demo6_producer_consumer;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 美团算法题: 手撕生产者消费者模型
 */

public class ProducerConsumer {

    private final Queue<Integer> products = new LinkedList<>();
    private Integer item = 1;
    private final Integer MAX_CAPACITY = 5;

    public void produce() {
        while (true) {
            synchronized (this) {
                // 1) 如果满了则无法生产商品
                if (products.size() == MAX_CAPACITY) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 2) 生产商品到队列
                System.out.println("Produce item: " + item);
                products.add(item ++);

                // 3) 通知消费者消费商品
                notifyAll();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void consume() {
        while (true) {
            synchronized (this) {
                // 1) 如果为空则无法消费商品
                if (products.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // 2) 消费商品
                System.out.println("Consume item: " + products.poll());

                // 3) 通知生产者生产商品
                notifyAll();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer();

        new Thread(pc::produce).start();
        new Thread(pc::consume).start();
    }
}
