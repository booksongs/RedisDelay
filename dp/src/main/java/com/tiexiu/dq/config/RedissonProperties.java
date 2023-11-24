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

}
