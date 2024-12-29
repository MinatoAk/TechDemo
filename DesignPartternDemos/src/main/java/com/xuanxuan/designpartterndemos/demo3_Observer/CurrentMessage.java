package com.xuanxuan.designpartterndemos.demo3_Observer;

public class CurrentMessage implements Observer {

    private WeatherData weatherData;
    String message;

    /**
     * 注册当前观察者
     * @param weatherData
     */
    public CurrentMessage(WeatherData weatherData) {
        this.weatherData = weatherData;
        weatherData.addObserver(this);
    }

    @Override
    public void update(String message) {
        this.message = message;
        System.out.println("Current Message: " + message);
    }
}
