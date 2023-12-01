package com.tiexiu.rd.example.listener;

import com.tiexiu.rd.example.domain.Order;
import com.tiexiu.rd.annotation.RedisListener;
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

