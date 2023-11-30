package com.tiexiu.dq.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.redis.delay")
public class RedissonProperties {
    /**
     * Database index used by the connection factory.
     */
    private int database = 0;

    /**
     * Connection URL. Overrides host, port, username, and password. Example:
     * redis://user:password@example.com:6379
     */
    private String url;

    private String host = "localhost";

    private String username;

    private String password;

    private int port = 6379;
    /**
     * 全局重试开关,优先级低于 RedisDelayQueue 注解的设置
     */
    private Boolean retry = false;

    /**
     * 尝试次数
     */
    private int retryAttempts = 0;

    /**
     * 重试保存策略
     */
    private String ConfirmPersistenceStrategy = "defaultConfirmPersistenceStrategy";

}
