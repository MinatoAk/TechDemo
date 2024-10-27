package com.xuanxuan.designpartterndemos.demo1_Singleton;

public class EagerInitialization {
    private static EagerInitialization instance = new EagerInitialization();

    private EagerInitialization() {
        System.out.println("饿汉式创建单例完成！");
    }

    public static EagerInitialization getInstance() {
        return instance;
    }
}
