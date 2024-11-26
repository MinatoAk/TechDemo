package com.xuanxuan.redisdemo.demo8_BitMap_SignIn;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
public class signInTest {
    @Resource
    private SignInService signInService;

    @Test
    public void test(){
        long userId = 1L;

        boolean success = signInService.addSignIn(userId);

        List<Integer> signInList = signInService.getSignIn(userId, LocalDate.now().getYear());

        System.out.println(signInList);
    }
}
