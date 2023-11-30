package com.tiexiu.dq.confirm;

import com.tiexiu.dq.listener.MethodRedisListenerEndpoint;
import com.tiexiu.dq.listener.Provider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("defaultConfirmPersistenceStrategy")
public class DefaultConfirmPersistenceStrategy extends AbstractConfirmPersistenceStrategy {

    @Override
    protected void saveRecord(Provider message, MethodRedisListenerEndpoint listenerEndpoint) {
        ConfirmUtil.addDeadLetterQueue(DEAD_LETTER, listenerEndpoint.getDelayRedisson(), new DeadLetterProvider(message,listenerEndpoint.getEndpoint()));
    }
}
