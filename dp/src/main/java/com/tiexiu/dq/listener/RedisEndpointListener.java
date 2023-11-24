package com.tiexiu.dq.listener;

import com.tiexiu.dq.config.DelayRedisson;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RedisEndpointListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 3,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            runnable -> new Thread(null, runnable, "delay-pool-" + POOL_NUMBER.getAndIncrement(), 0));

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        //在应用程序启动完成后执行
        log.info("redisEndpointListener is running");
        Executors.newSingleThreadScheduledExecutor()
                .scheduleAtFixedRate(() -> RedisListenerEndpointRegistrar.listenerEndpoints.parallelStream()
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
            } catch (Exception e) {
                log.error("", e);
            }
        }
    }
}

