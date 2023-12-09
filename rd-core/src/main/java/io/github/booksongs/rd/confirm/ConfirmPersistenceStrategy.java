package io.github.booksongs.rd.confirm;

import io.github.booksongs.rd.listener.MethodRedisListenerEndpoint;

public interface ConfirmPersistenceStrategy {
    void doRetryRecord(int retryAttempts, Object message, MethodRedisListenerEndpoint listenerEndpoint);
}
