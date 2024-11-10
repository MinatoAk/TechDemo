package com.xuanxuan.concurrentdemos.demo2_sequentialExe;

public class joinTestMain {

    static class Thread1 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread1 is running!");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread1 is over!");
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            System.out.println("Thread2 is running!");
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Thread2 is over!");
        }
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("程序执行时间: " + (endTime - startTime) + "ms");
    }
}
