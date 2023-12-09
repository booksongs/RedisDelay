package io.github.booksongs.rd.annotation;

import io.github.booksongs.rd.config.RedissonFactoryBuilder;
import io.github.booksongs.rd.config.RedissonProperties;
import io.github.booksongs.rd.confirm.DefaultConfirmPersistenceStrategy;
import io.github.booksongs.rd.listener.RedisEndpointListener;
import io.github.booksongs.rd.util.DelayTemplate;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RedisListenerConfigurationSelector.class, RedissonFactoryBuilder.class,
        RedissonProperties.class, RedisEndpointListener.class,
        DefaultConfirmPersistenceStrategy.class, DelayTemplate.class
})
public @interface EnableRedisDelay {
}
