package com.xuanxuan.concurrentdemos.demo2_sequentialExe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class printABCTest implements Runnable {

    private final int printCount = 5;

    private final ReentrantLock lock;
    private final Condition curCondition;
    private final Condition nextCondition;
    private final char thisThreadChar;

    printABCTest(ReentrantLock lock, Condition curCondition, Condition nextCondition, char thisThreadChar) {
        this.lock = lock;
        this.curCondition = curCondition;
        this.nextCondition = nextCondition;
        this.thisThreadChar = thisThreadChar;
    }

    @Override
    public void run() {
        for (int i = 0; i < printCount; i ++ ) {
            lock.lock();
            try {
                // 1. 打印当前字符
                System.out.println(thisThreadChar);

                // 2. 通知下一个线程打印下一个字符
                nextCondition.signal();

                // 3. 当前线程等待，最后一次不需要等待
                if (i != printCount - 1) {
                    try {
                        curCondition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            } finally {
                lock.unlock();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition conditionA = lock.newCondition();
        Condition conditionB = lock.newCondition();
        Condition conditionC = lock.newCondition();

        Thread threadA = new Thread(new printABCTest(lock, conditionA, conditionB, 'A'));
        Thread threadB = new Thread(new printABCTest(lock, conditionB, conditionC, 'B'));
        Thread threadC = new Thread(new printABCTest(lock, conditionC, conditionA, 'C'));

        // 这里注意一定要依次开启任务
        threadA.start();
        Thread.sleep(100);
        threadB.start();
        Thread.sleep(100);
        threadC.start();
    }
}
