package com.tiexiu.dq.listener;

import com.tiexiu.dq.annotation.RedisListener;
import com.tiexiu.dq.config.DelayRedisson;
import com.tiexiu.dq.config.RedissonProperties;
import com.tiexiu.dq.confirm.ConfirmPersistenceStrategy;
import com.tiexiu.dq.confirm.ConfirmUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RedisEndpointListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);

    private ApplicationContext applicationContext;
    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 3,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            runnable -> new Thread(null, runnable, "delay-pool-" + POOL_NUMBER.getAndIncrement(), 0));

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        applicationContext = event.getApplicationContext();
        //在应用程序启动完成后执行, 50毫秒为周期执行任务
        log.info("redisEndpointListener is running");
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> RedisListenerEndpointRegistrar.listenerEndpoints.parallelStream()
                        .forEach(listenerEndpoint ->
                                executorService.execute(
                                        () -> processListenerEndpoint(listenerEndpoint))),
                0, 50, TimeUnit.MILLISECONDS);
    }

    private void processListenerEndpoint(MethodRedisListenerEndpoint listenerEndpoint) {
        DelayRedisson delayRedisson = listenerEndpoint.getDelayRedisson();
        RBlockingDeque<Provider> blockingDeque = delayRedisson.getBlockingDeque(listenerEndpoint.getEndpoint());
        delayRedisson.getDelayedQueue(blockingDeque);
        Provider message = blockingDeque.poll();
        if (message != null) {
            try {
                if (log.isDebugEnabled()) {
                    log.debug("收到的消息: {}", message);
                }
                listenerEndpoint.getMethod().invoke(listenerEndpoint.getBean(), message);
                //确认消费
                ConfirmUtil.del(ConfirmUtil.RETRY + listenerEndpoint.getEndpoint(), delayRedisson);
            } catch (Exception e) {
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

    private void retryRecord(ConfirmPersistenceStrategy strategy, int retryAttempts, Provider message, MethodRedisListenerEndpoint listenerEndpoint) {
        strategy.doRetryRecord(retryAttempts, message, listenerEndpoint);
    }

}

