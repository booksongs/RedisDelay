package com.tiexiu.rd.listener;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RedisListenerEndpointRegistrar {
    static List<MethodRedisListenerEndpoint> listenerEndpoints = new ArrayList<>();

    public void registerEndpoint(MethodRedisListenerEndpoint methodRedisListenerEndpoint) {
        listenerEndpoints.add(methodRedisListenerEndpoint);
    }
}
