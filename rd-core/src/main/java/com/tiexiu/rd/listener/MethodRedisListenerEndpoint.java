package com.tiexiu.rd.listener;

import com.tiexiu.rd.config.DelayRedisson;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public class MethodRedisListenerEndpoint {
    private String endpoint;
    private Method method;
    private Object bean;
    private DelayRedisson delayRedisson;
}
