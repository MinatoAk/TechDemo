package com.xuanxuan.concurrentdemos.demo5_print123;

import java.util.concurrent.atomic.AtomicInteger;

public class Print123 {
    /**
     * 字节算法题: 使用三个线程连续交替打印 123
     *
     * 基本思路: 拿到锁的线程才能去打印，只有能打印当前数字的线程才能拿到锁
     */

    static Object lock = new Object();

    // 这里用 int 其实也完全没有问题
    static AtomicInteger number2Print = new AtomicInteger(1);

    public static void main(String[] args) {
        Printer printer1 = new Printer(1);
        Printer printer2 = new Printer(2);
        Printer printer3 = new Printer(3);

        new Thread(printer1).start();
        new Thread(printer2).start();
        new Thread(printer3).start();
    }

    static class Printer implements Runnable {

        private Integer number;

        public Printer(Integer number) {
            this.number = number;
        }

        @Override
        public void run() {
            for (int i = 0; i < 3; i ++ ) {
                synchronized (lock) {
                    // 1) 如果不是当前数字打印，则阻塞等锁
                    while (number != number2Print.get()) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // 2) 拿到锁，打印当前数字，并且更新下一个打印的数字
                    System.out.println(number);
                    if (number2Print.get() == 3) {
                        number2Print.set(1);
                    } else {
                        number2Print.getAndIncrement();
                    }

                    // 3) 释放锁，唤醒其他线程
                    lock.notifyAll();
                }
            }
        }
    }
}
