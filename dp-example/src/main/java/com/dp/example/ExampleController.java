package com.dp.example;

import com.tiexiu.dq.config.DelayRedisson;
import com.tiexiu.dq.util.RedissonDelayedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * 延迟队列测试
 */
@RestController
public class ExampleController {

    @Autowired
    private DelayRedisson delayRedisson;

    @Resource
    private DelayRedisson customRedissonDelayQueue;

    @GetMapping("/addQueue")
    public void addQueue() {
//        RedissonDelayedQueue.offer(new Order("购买小米", "小米14"),
//                5, TimeUnit.SECONDS, Constant.ORDER_PAYMENT_TIMEOUT,
//                delayRedisson
//        );
        RedissonDelayedQueue.offer(new Order("购买小米", "小米14"),
                6, TimeUnit.SECONDS, Constant.ORDER_TIMEOUT_NOT_EVALUATED,
                customRedissonDelayQueue
        );
    }


}

