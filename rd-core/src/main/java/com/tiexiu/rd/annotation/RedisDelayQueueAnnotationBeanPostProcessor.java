package com.tiexiu.rd.annotation;

import com.tiexiu.rd.config.DelayRedisson;
import com.tiexiu.rd.listener.MethodRedisListenerEndpoint;
import com.tiexiu.rd.listener.RedisListenerEndpointRegistrar;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;

import java.lang.reflect.Method;

@Slf4j
public class RedisDelayQueueAnnotationBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {

    public static final String DEFAULT_CONTAINER_FACTORY_NAME = "delayRedisson";
    private BeanFactory beanFactory;

    @Nullable
    public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
        Class<?> targetClass = AopUtils.getTargetClass(bean);
        for (Method method : targetClass.getDeclaredMethods()) {
            RedisListener annotation = method.getAnnotation(RedisListener.class);
            if (annotation != null) {
                RedisListenerEndpointRegistrar redisListenerEndpointRegistrar = new RedisListenerEndpointRegistrar();
                String containerFactory = annotation.containerFactory();
                if (containerFactory.isEmpty()) {
                    containerFactory = DEFAULT_CONTAINER_FACTORY_NAME;
                }
                if (log.isDebugEnabled()) {
                    log.debug("scan to RedisDelayQueue annotation, value:{} - containerFactory: {}", annotation.value(), containerFactory);
                }
                redisListenerEndpointRegistrar.registerEndpoint(new MethodRedisListenerEndpoint(annotation.value(), method, bean,
                        beanFactory.getBean(containerFactory, DelayRedisson.class)));

            }
        }
        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
