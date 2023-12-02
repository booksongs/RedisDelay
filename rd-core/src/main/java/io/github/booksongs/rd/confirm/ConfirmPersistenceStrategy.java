package io.github.booksongs.rd.confirm;

import io.github.booksongs.rd.listener.MethodRedisListenerEndpoint;
import io.github.booksongs.rd.listener.Provider;

public interface ConfirmPersistenceStrategy {
    void doRetryRecord(int retryAttempts, Provider message, MethodRedisListenerEndpoint listenerEndpoint);
}
