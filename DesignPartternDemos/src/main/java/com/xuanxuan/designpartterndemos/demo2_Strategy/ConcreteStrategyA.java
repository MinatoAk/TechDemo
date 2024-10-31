package com.xuanxuan.designpartterndemos.demo2_Strategy;

@StrategyConfig(strategyType = 1)
public class ConcreteStrategyA implements Strategy {

    @Override
    public String doOperation() {
        return "Hello, This is StrategyA!";
    }
}
