package com.tiexiu.dq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RedisDelayQueue {

    /**
     * 延时队列名
     */
    String value();

    String containerFactory() default "";
}
