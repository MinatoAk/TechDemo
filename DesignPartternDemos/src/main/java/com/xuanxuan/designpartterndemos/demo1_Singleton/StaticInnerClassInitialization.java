package com.xuanxuan.designpartterndemos.demo1_Singleton;

public class StaticInnerClassInitialization {
    private static class SingletonHolder {
        private static final StaticInnerClassInitialization INSTANCE = new StaticInnerClassInitialization();
    }

    private StaticInnerClassInitialization() {
        System.out.println("静态内部类创建单例完成！");
    }

    public static StaticInnerClassInitialization getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
