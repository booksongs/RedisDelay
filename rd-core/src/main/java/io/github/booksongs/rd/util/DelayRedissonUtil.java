package io.github.booksongs.rd.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.booksongs.rd.config.DelayRedisson;
import io.github.booksongs.rd.listener.Provider;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DelayRedissonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static DelayRedisson delayRedisson;

    public static void setDelayRedisson(DelayRedisson delayRedisson) {
        DelayRedissonUtil.delayRedisson = delayRedisson;
    }

    public static <T extends Provider> void offer(T value, long delay, String topic) {
        try {
            if (delay < 0) {
                delay = 0;
            }
            RBlockingDeque<T> blockingDeque = delayRedisson.getBlockingDeque(topic);
            RDelayedQueue<T> delayedQueue = delayRedisson.getDelayedQueue(blockingDeque);
            value.setId(UUID.randomUUID().toString());
            value.setSendTime(System.currentTimeMillis());
            delayedQueue.offer(value, delay, TimeUnit.SECONDS);
            if (log.isDebugEnabled()) {
                log.info("Adding the delay queue succeeded. Queue key：{}，Queue value：{}，Delay time：{}",
                        topic, objectMapper.writeValueAsString(value), delay + "seconds");
            }
        } catch (Exception e) {
            log.error("Failed to add a delay queue. Procedure", e);
        }
    }

    public static <T extends Provider> void offer(T value, long delay, String topic, DelayRedisson delayRedisson) {
        try {
            if (delay < 0) {
                delay = 0;
            }
            RBlockingDeque<T> blockingDeque = delayRedisson.getBlockingDeque(topic);
            RDelayedQueue<T> delayedQueue = delayRedisson.getDelayedQueue(blockingDeque);
            value.setId(UUID.randomUUID().toString());
            value.setSendTime(System.currentTimeMillis());
            delayedQueue.offer(value, delay, TimeUnit.SECONDS);
            if (log.isDebugEnabled()) {
                log.info("Adding the delay queue succeeded. Queue key：{}，Queue value：{}，Delay time：{}",
                        topic, objectMapper.writeValueAsString(value), delay + "seconds");
            }
        } catch (Exception e) {
            log.error("Failed to add a delay queue. Procedure", e);
        }
    }

}
