package com.xuanxuan.springdemo.config;

import com.xuanxuan.springdemo.demo1_Interceptor.ExecuteTimeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ExecuteTimeInterceptor())
                .addPathPatterns("/**");
    }
}
