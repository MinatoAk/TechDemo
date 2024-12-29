package com.xuanxuan.designpartterndemos.demo3_Observer;

public class ObserverTestMain {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        Observer observer = new CurrentMessage(weatherData);

        weatherData.setMessage("今天天气晴朗");
    }
}
