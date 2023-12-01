package com.tiexiu.rd.confirm;

import com.tiexiu.rd.config.DelayRedisson;
import com.tiexiu.rd.listener.MethodRedisListenerEndpoint;
import com.tiexiu.rd.listener.Provider;
import com.tiexiu.rd.util.DelayRedissonUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;

@Slf4j
public abstract class AbstractConfirmPersistenceStrategy implements ConfirmPersistenceStrategy {

    protected static final String DEAD_LETTER = "REDIS_DEAD_LETTER_QUEUE";

    public void doRetryRecord(int retryAttempts, Provider message, MethodRedisListenerEndpoint listenerEndpoint) {
        DelayRedisson delayRedisson = listenerEndpoint.getDelayRedisson();
        String retryKey = ConfirmUtil.RETRY + listenerEndpoint.getEndpoint();
        RAtomicLong atomic = ConfirmUtil.getAtomicLong(retryKey, delayRedisson);
        if (atomic.get() < retryAttempts) {
            log.info("消息处理失败,尝试进行第{}次重试", atomic.get() + 1);
            DelayRedissonUtil.offer(message, 3, listenerEndpoint.getEndpoint(), delayRedisson);
            ConfirmUtil.incrementAndGet(retryKey, delayRedisson);
        } else {
            log.info("超过最大重试次数，不再重试");
            ConfirmUtil.del(retryKey, delayRedisson);
            this.saveRecord(message, listenerEndpoint);
        }
    }

    protected abstract void saveRecord(Provider message, MethodRedisListenerEndpoint listenerEndpoint);
}
