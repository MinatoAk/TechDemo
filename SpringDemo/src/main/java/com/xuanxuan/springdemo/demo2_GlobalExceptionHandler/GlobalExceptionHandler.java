package com.xuanxuan.springdemo.demo2_GlobalExceptionHandler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public void businessExceptionHandler(BusinessException e) {
        /**
         * 这边是不会输出的，直接就异常抛掉了，一般的处理流程如下：
         * 1. 记录异常到日志中
         * 2. 返回给前端 ResultUtil 封装好的错误码和错误信息
         */
        System.out.println("异常错误码: " + e.getCode() + " | 异常信息: " + e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public void runtimeExceptionHandler(RuntimeException e) {
        System.out.println("异常错误码: " + ErrorCode.SYSTEM_ERROR + " | 异常信息: " + e.getMessage());
    }
}
