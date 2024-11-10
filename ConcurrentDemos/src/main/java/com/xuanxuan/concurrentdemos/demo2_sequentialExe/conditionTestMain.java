package com.xuanxuan.concurrentdemos.demo2_sequentialExe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class conditionTestMain {

    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void conditionWait() {
        lock.lock();

        try {
            // 1. 阻塞，等待其他线程先完成
            System.out.println(Thread.currentThread().getName() + ": Condition Wait start!");
            condition.await();

            // 2. 被唤醒，开始执行
            System.out.println(Thread.currentThread().getName() + " is Notified!");
            // 执行具体的逻辑
            System.out.println(Thread.currentThread().getName() + " is running!");

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void conditionSignal() {
        lock.lock();
        try {
            // 1. 执行自己具体的逻辑
            try {
                System.out.println(Thread.currentThread().getName() + " is running!");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            // 2. 自己完成之后通知其他等待的线程
            System.out.println(Thread.currentThread().getName() + ": Notifying...");
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        conditionTestMain conditionController = new conditionTestMain();

        Thread thread1 = new Thread(() -> {
            System.out.println("Thread0 start!");
            conditionController.conditionWait();
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Thread1 start!");
            conditionController.conditionSignal();
        });

        thread1.start();
        thread2.start();
    }
}
