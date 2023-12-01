package com.tiexiu.rd.annotation;

import com.tiexiu.rd.config.RedissonFactoryBuilder;
import com.tiexiu.rd.config.RedissonProperties;
import com.tiexiu.rd.confirm.DefaultConfirmPersistenceStrategy;
import com.tiexiu.rd.listener.RedisEndpointListener;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisListenerConfigurationSelector.class, RedissonFactoryBuilder.class,
        RedissonProperties.class, RedisEndpointListener.class,
        DefaultConfirmPersistenceStrategy.class
})
public @interface EnableRedisDelay {
}
