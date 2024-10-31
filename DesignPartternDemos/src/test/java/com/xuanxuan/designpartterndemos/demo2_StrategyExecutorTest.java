package com.xuanxuan.designpartterndemos;

import com.xuanxuan.designpartterndemos.demo2_Strategy.StrategyExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class demo2_StrategyExecutorTest {

    @Resource
    private StrategyExecutor strategyExecutor;

    @Test
    public void test() {
        String resA = strategyExecutor.executeStrategy(1);
        String resB = strategyExecutor.executeStrategy(2);

        System.out.println("StrategyType = 1: " + resA);
        System.out.println("StrategyType = 2: " + resB);
    }

}
