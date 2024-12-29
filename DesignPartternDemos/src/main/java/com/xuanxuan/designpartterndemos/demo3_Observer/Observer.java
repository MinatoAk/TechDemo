package com.xuanxuan.designpartterndemos.demo3_Observer;

public interface Observer {
    /**
     * 观察者监听到信息后更新
     */
    void update(String message);
}
