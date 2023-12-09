package io.github.booksongs.rd.confirm;

import io.github.booksongs.rd.listener.MethodRedisListenerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("defaultConfirmPersistenceStrategy")
public class DefaultConfirmPersistenceStrategy extends AbstractConfirmPersistenceStrategy {

    @Override
    protected void saveRecord(Object message, MethodRedisListenerEndpoint listenerEndpoint) {
        ConfirmUtil.addDeadLetterQueue(DEAD_LETTER, listenerEndpoint.getDelayRedisson(), new DeadLetterProvider(message,listenerEndpoint.getEndpoint()));
    }

}
