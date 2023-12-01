package com.tiexiu.dp.example;

import com.tiexiu.dq.confirm.AbstractConfirmPersistenceStrategy;
import com.tiexiu.dq.listener.MethodRedisListenerEndpoint;
import com.tiexiu.dq.listener.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JdbcConfirmPersistenceStrategy extends AbstractConfirmPersistenceStrategy {

    @Override
    protected void saveRecord(Provider message, MethodRedisListenerEndpoint listenerEndpoint) {

    }
}
