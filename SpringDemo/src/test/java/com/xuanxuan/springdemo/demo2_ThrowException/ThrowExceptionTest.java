package com.xuanxuan.springdemo.demo2_ThrowException;

import com.xuanxuan.springdemo.demo2_GlobalExceptionHandler.BusinessException;
import com.xuanxuan.springdemo.demo2_GlobalExceptionHandler.ErrorCode;
import com.xuanxuan.springdemo.demo2_GlobalExceptionHandler.ThrowUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThrowExceptionTest {

    @Test
    public void test() {
        try {
            ThrowUtil.throwIf(true, ErrorCode.FORBIDDEN_ERROR, "被禁止访问啦！");
        } catch (BusinessException e) {
            System.out.println("异常错误码: " + e.getCode() + " | 异常信息: " + e.getMessage());
        }
    }
}
