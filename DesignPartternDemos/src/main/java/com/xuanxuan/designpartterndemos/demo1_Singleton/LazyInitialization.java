package com.xuanxuan.designpartterndemos.demo1_Singleton;

public class LazyInitialization {
    private static LazyInitialization instance;

    private LazyInitialization() {
        System.out.println("懒汉式创建单例完成！");
    }

    public static synchronized LazyInitialization getInstance() {
        if (instance == null) {
            instance = new LazyInitialization();
        }
        return instance;
    }
}
