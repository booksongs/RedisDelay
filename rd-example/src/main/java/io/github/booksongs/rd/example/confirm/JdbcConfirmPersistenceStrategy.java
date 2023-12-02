package io.github.booksongs.rd.example.confirm;

import io.github.booksongs.rd.confirm.AbstractConfirmPersistenceStrategy;
import io.github.booksongs.rd.listener.MethodRedisListenerEndpoint;
import io.github.booksongs.rd.listener.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 确认持久化策略,失败超过重试次数的的消息会进行持久化
 * 默认是存在redis中,key=REDIS_DEAD_LETTER_QUEUE
 * 这里我们可以拓展自己的存储实现,存在数据库中
 *
 * 要开启这个策略,需要在application.yml中配置
 * spring.redis.delay.confirmPersistenceStrategy = bean的name
 */
@Slf4j
@Component
public class JdbcConfirmPersistenceStrategy extends AbstractConfirmPersistenceStrategy {

    @Override
    protected void saveRecord(Provider message, MethodRedisListenerEndpoint listenerEndpoint) {

    }
}
