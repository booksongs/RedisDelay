package com.tiexiu.dq.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RedisListener {

    /**
     * 延时队列名
     */
    String value();

    String containerFactory() default "";

    /**
     * 是否重试
     */
    boolean isRetry() default false;

    /**
     * 重试次数
     */
    int retryAttempts() default 0;
}
