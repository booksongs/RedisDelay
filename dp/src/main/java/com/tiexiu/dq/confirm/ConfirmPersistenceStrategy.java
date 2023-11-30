package com.tiexiu.dq.confirm;

import com.tiexiu.dq.listener.MethodRedisListenerEndpoint;
import com.tiexiu.dq.listener.Provider;

public interface ConfirmPersistenceStrategy {
    void doRetryRecord(int retryAttempts, Provider message, MethodRedisListenerEndpoint listenerEndpoint);
}
