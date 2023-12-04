package io.github.booksongs.rd.example.listener;

import io.github.booksongs.rd.annotation.RedisListener;
import io.github.booksongs.rd.example.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ListenerExample {

    @RedisListener(value = "ORDER_PAYMENT_TIMEOUT")
    public void example1(Order order) {
        log.info("ORDER_PAYMENT_TIMEOUT:{}", order);
    }

    @RedisListener(value = "ORDER_TIMEOUT_NOT_EVALUATED",
            containerFactory = "customRedissonDelayQueue",
            isRetry = true,
            retryAttempts = 3
    )
    public void example2(Order order) {
        log.info("ORDER_TIMEOUT_NOT_EVALUATED:{}", order);
//        throw new RuntimeException("test");
    }

}

