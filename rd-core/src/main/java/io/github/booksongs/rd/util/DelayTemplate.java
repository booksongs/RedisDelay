package io.github.booksongs.rd.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.booksongs.rd.config.DelayRedisson;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.concurrent.TimeUnit;

@Slf4j
public class DelayTemplate {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    @Qualifier("delayRedisson")
    private DelayRedisson delayRedisson;

    public <T> void offer(T value, long delay, String topic) {
        try {
            if (delay < 0) {
                delay = 0;
            }
            RBlockingDeque<T> blockingDeque = delayRedisson.getBlockingDeque(topic);
            RDelayedQueue<T> delayedQueue = delayRedisson.getDelayedQueue(blockingDeque);
            delayedQueue.offer(value, delay, TimeUnit.SECONDS);
            if (log.isDebugEnabled()) {
                log.info("Adding the delay queue succeeded. Queue key：{}，Queue value：{}，Delay time：{}",
                        topic, objectMapper.writeValueAsString(value), delay + "seconds");
            }
        } catch (Exception e) {
            log.error("Failed to add a delay queue. Procedure", e);
        }
    }

    public <T> void offer(T value, long delay, String topic, DelayRedisson delayRedisson) {
        try {
            if (delay < 0) {
                delay = 0;
            }
            RBlockingDeque<T> blockingDeque = delayRedisson.getBlockingDeque(topic);
            RDelayedQueue<T> delayedQueue = delayRedisson.getDelayedQueue(blockingDeque);
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
