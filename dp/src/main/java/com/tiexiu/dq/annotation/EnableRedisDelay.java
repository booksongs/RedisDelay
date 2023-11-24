package com.tiexiu.dq.annotation;

import com.tiexiu.dq.config.RedissonFactoryBuilder;
import com.tiexiu.dq.config.RedissonProperties;
import com.tiexiu.dq.listener.RedisEndpointListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisListenerConfigurationSelector.class, RedissonFactoryBuilder.class,
        RedissonProperties.class, RedisEndpointListener.class})
public @interface EnableRedisDelay {
}
