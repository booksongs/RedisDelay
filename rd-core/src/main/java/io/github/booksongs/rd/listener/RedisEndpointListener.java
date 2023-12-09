package io.github.booksongs.rd.listener;

import io.github.booksongs.rd.annotation.RedisListener;
import io.github.booksongs.rd.config.DelayRedisson;
import io.github.booksongs.rd.config.RedissonProperties;
import io.github.booksongs.rd.confirm.ConfirmPersistenceStrategy;
import io.github.booksongs.rd.confirm.ConfirmUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RedisEndpointListener implements ApplicationListener<ContextRefreshedEvent> {
    boolean isInitialized = false;

    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

    private ApplicationContext applicationContext;

    private RedissonProperties redissonProperties;

    private ExecutorService executorService;


    @Override
    public synchronized void onApplicationEvent(ContextRefreshedEvent event) {
        synchronized (RedisEndpointListener.class) {
            if (!isInitialized) {
                this.init(event);
                //在应用程序启动完成后执行
                log.info("redisEndpointListener is running");
                Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                        () -> RedisListenerEndpointRegistrar.listenerEndpoints.parallelStream()
                                .forEach(listenerEndpoint ->
                                        executorService.execute(
                                                () -> processListenerEndpoint(listenerEndpoint))),
                        0, redissonProperties.getRefreshCycle(), TimeUnit.MILLISECONDS);
                isInitialized = true;
            }
        }
    }

    private void init(ApplicationContextEvent event) {
        applicationContext = event.getApplicationContext();
        redissonProperties = applicationContext.getBean(RedissonProperties.class);
        executorService = new ThreadPoolExecutor(redissonProperties.getCorePollSize(), redissonProperties.getCorePollSize(),
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                runnable -> new Thread(new ThreadGroup("delay-pool-group"), runnable, "delay-pool-" + POOL_NUMBER.getAndIncrement(), 0));
    }

    private void processListenerEndpoint(MethodRedisListenerEndpoint listenerEndpoint) {
        DelayRedisson delayRedisson = listenerEndpoint.getDelayRedisson();
        RBlockingDeque<Object> blockingDeque = delayRedisson.getBlockingDeque(listenerEndpoint.getEndpoint());
        delayRedisson.getDelayedQueue(blockingDeque);
        Object message = blockingDeque.poll();
        if (message != null) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("收到的消息: {}", message);
                }
                Method method = listenerEndpoint.getMethod();
                ReflectionUtils.invokeMethod(method, listenerEndpoint.getBean(), MessageConverter.convert(message, method));
                //确认消费
                ConfirmUtil.del(ConfirmUtil.RETRY + listenerEndpoint.getEndpoint(), delayRedisson);
            } catch (Exception e) {
                log.error("", e);
                RedissonProperties properties = applicationContext.getBean(RedissonProperties.class);
                RedisListener annotation = listenerEndpoint.getMethod().getAnnotation(RedisListener.class);
                Map<String, ConfirmPersistenceStrategy> beansOfType =
                        applicationContext.getBeansOfType(ConfirmPersistenceStrategy.class);
                ConfirmPersistenceStrategy confirmPersistenceStrategy =
                        beansOfType.get(properties.getConfirmPersistenceStrategy());

                // 获取注解参数
                if (annotation.isRetry()) {
                    this.retryRecord(confirmPersistenceStrategy, annotation.retryAttempts(), message, listenerEndpoint);
                    return;
                }

                if (properties.getRetry()) {
                    this.retryRecord(confirmPersistenceStrategy, properties.getRetryAttempts(), message, listenerEndpoint);
                }

            }

        }
    }

    private void retryRecord(ConfirmPersistenceStrategy strategy, int retryAttempts, Object message, MethodRedisListenerEndpoint listenerEndpoint) {
        strategy.doRetryRecord(retryAttempts, message, listenerEndpoint);
    }
}

