package com.xuanxuan.designpartterndemos.demo2_Strategy;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StrategyExecutor {

    @Resource
    private List<Strategy> strategies;

    public String executeStrategy(int strategyType) {
        for (Strategy strategy : strategies) {
            if (strategy.getClass().isAnnotationPresent(StrategyConfig.class)) {
                StrategyConfig strategyConfig = strategy.getClass().getAnnotation(StrategyConfig.class);
                if (strategyConfig.strategyType() == strategyType) {
                    return strategy.doOperation();
                }
            }
        }

        return "Error, no strategy found!";
    }
}
