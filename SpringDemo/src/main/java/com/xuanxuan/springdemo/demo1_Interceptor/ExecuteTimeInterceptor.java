package com.xuanxuan.springdemo.demo1_Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExecuteTimeInterceptor implements HandlerInterceptor {

    private long startTime;
    private long endTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        startTime = System.currentTimeMillis();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        endTime = System.currentTimeMillis();
        System.out.println("Request URL: " + request.getRequestURL() + " | Time Taken: " + (endTime - startTime) + " ms");
    }
}
