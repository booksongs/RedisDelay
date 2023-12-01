package com.tiexiu.rd.confirm;

import com.tiexiu.rd.listener.MethodRedisListenerEndpoint;
import com.tiexiu.rd.listener.Provider;

public interface ConfirmPersistenceStrategy {
    void doRetryRecord(int retryAttempts, Provider message, MethodRedisListenerEndpoint listenerEndpoint);
}
