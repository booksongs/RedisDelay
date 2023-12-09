package io.github.booksongs.rd.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 消息转换器
 * 只针对第一个参数
 */
public class MessageConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 转换消息,支持string,map,只支持第一个参数进行转换
     * @param message
     * @param method
     * @return
     */
    public static Object[] convert(Object message, Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        Object[] parameters = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i == 0) {
                Class<?> parameterType = parameterTypes[i];
                if (String.class.isAssignableFrom(parameterType)) {
                    try {
                        parameters[i] = objectMapper.writeValueAsString(message);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                } else if (Map.class.isAssignableFrom(parameterType)) {
                    // 将对象转换为 Map
                    parameters[i] = objectMapper.convertValue(message, new TypeReference<Map<String, Object>>() {
                    });
                } else {
                    parameters[i] = message;
                }
            } else {
                parameters[i] = null;
            }
        }
        return parameters;
    }
}
