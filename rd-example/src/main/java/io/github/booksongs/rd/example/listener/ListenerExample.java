package io.github.booksongs.rd.example.listener;

import com.alibaba.fastjson2.JSONObject;
import io.github.booksongs.rd.annotation.RedisListener;
import io.github.booksongs.rd.example.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ListenerExample {

    @RedisListener(value = "ORDER_PAYMENT_TIMEOUT")
    public void example1(Map order, String aaa) {
        log.info("ORDER_PAYMENT_TIMEOUT:{}", JSONObject.toJSONString(order));
    }

//    @RedisListener(value = "ORDER_TIMEOUT_NOT_EVALUATED",
//            containerFactory = "customRedissonDelayQueue",
//            isRetry = true,
//            retryAttempts = 3
//    )
    public void example2(Order order) {
        log.info("ORDER_TIMEOUT_NOT_EVALUATED:{}", order);
        throw new RuntimeException("test");
    }

}

