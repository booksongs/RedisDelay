package io.github.booksongs.rd.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.lang.Nullable;

public class RedisDelayBootstrapConfiguration implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(@Nullable AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        registry.registerBeanDefinition(RedisDelayQueueAnnotationBeanPostProcessor.class.getName(),
                new RootBeanDefinition(RedisDelayQueueAnnotationBeanPostProcessor.class));
    }
}
