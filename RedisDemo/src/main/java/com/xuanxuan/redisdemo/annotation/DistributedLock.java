package com.xuanxuan.redisdemo.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {

    String key();

    long releaseTime() default 30 * 1000;

    long waitTime() default 10 * 1000;

    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
