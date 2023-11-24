package com.tiexiu.dq.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiexiu.dq.config.DelayRedisson;
import com.tiexiu.dq.listener.Provider;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedissonDelayedQueue {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T extends Provider> void offer(T value, long delay, TimeUnit timeUnit, String topic, DelayRedisson delayRedisson) {
        try {
            if (delay < 0) {
                delay = 0;
            }
            RBlockingDeque<T> blockingDeque = delayRedisson.getBlockingDeque(topic);
            RDelayedQueue<T> delayedQueue = delayRedisson.getDelayedQueue(blockingDeque);
            value.setId(UUID.randomUUID().toString());
            value.setSendTime(System.currentTimeMillis());
            delayedQueue.offer(value, delay, timeUnit);
            if (log.isDebugEnabled()) {
                log.info("Adding the delay queue succeeded. Queue key：{}，Queue value：{}，Delay time：{}",
                        topic, objectMapper.writeValueAsString(value), timeUnit.toSeconds(delay) + "seconds");
            }
        } catch (Exception e) {
            log.error("Failed to add a delay queue. Procedure", e);
        }
    }

}
