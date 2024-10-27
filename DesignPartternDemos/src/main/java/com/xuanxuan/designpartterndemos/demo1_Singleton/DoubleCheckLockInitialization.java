package com.xuanxuan.designpartterndemos.demo1_Singleton;

public class DoubleCheckLockInitialization {
    private static volatile DoubleCheckLockInitialization instance;

    private DoubleCheckLockInitialization() {
        System.out.println("双检锁创建单例完成！");
    }

    public static DoubleCheckLockInitialization getInstance() {
        if (instance == null) {
            synchronized (DoubleCheckLockInitialization.class) {
                if (instance == null)
                    instance = new DoubleCheckLockInitialization();
            }
        }
        return instance;
    }
}
