package io.github.booksongs.rd.confirm;

import io.github.booksongs.rd.config.DelayRedisson;
import io.github.booksongs.rd.listener.MethodRedisListenerEndpoint;
import io.github.booksongs.rd.util.DelayTemplate;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public abstract class AbstractConfirmPersistenceStrategy implements ConfirmPersistenceStrategy {

    protected static final String DEAD_LETTER = "REDIS_DEAD_LETTER_QUEUE";

    @Autowired
    private DelayTemplate delayTemplate;

    public void doRetryRecord(int retryAttempts, Object message, MethodRedisListenerEndpoint listenerEndpoint) {
        DelayRedisson delayRedisson = listenerEndpoint.getDelayRedisson();
        String retryKey = ConfirmUtil.RETRY + listenerEndpoint.getEndpoint();
        RAtomicLong atomic = ConfirmUtil.getAtomicLong(retryKey, delayRedisson);
        if (atomic.get() < retryAttempts) {
            log.info("消息处理失败,尝试进行第{}次重试", atomic.get() + 1);
            delayTemplate.offer(message, 3, listenerEndpoint.getEndpoint(), delayRedisson);
            ConfirmUtil.incrementAndGet(retryKey, delayRedisson);
        } else {
            log.info("超过最大重试次数，不再重试");
            ConfirmUtil.del(retryKey, delayRedisson);
            this.saveRecord(message, listenerEndpoint);
        }
    }

    protected abstract void saveRecord(Object message, MethodRedisListenerEndpoint listenerEndpoint);
}
