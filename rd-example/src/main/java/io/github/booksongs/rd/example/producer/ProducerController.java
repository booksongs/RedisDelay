package io.github.booksongs.rd.example.producer;


import io.github.booksongs.rd.config.DelayRedisson;
import io.github.booksongs.rd.example.domain.Order;
import io.github.booksongs.rd.util.DelayRedissonUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 延迟队列测试
 */
@RestController
public class ProducerController {

    @Resource
    private DelayRedisson customRedissonDelayQueue;

    @GetMapping("/addQueue")
    public void addQueue() {
        DelayRedissonUtil.offer(new Order("购买小米", "小米14"), 5, "ORDER_PAYMENT_TIMEOUT");

        DelayRedissonUtil.offer(new Order("购买小米", "小米14"), 6, "ORDER_TIMEOUT_NOT_EVALUATED", customRedissonDelayQueue);
    }

}

