package com.xuanxuan.designpartterndemos.demo2_Strategy;

@StrategyConfig(strategyType = 2)
public class ConcreteStrategyB implements Strategy {

    @Override
    public String doOperation() {
        return "Hello, This is StrategyB!";
    }
}
