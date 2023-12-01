package com.tiexiu.dq.config;

import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RedissonFactoryBuilder {

    @Bean
    @Primary
    public DelayRedisson redissonDelayQueue(RedissonProperties redissonProperties) {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        //ping 5s
        singleServerConfig.setPingConnectionInterval(5000);
        singleServerConfig.setAddress("redis://" + redissonProperties.getHost() + ":" + redissonProperties.getPort());
        singleServerConfig.setPassword(redissonProperties.getPassword());
        singleServerConfig.setKeepAlive(true);
        singleServerConfig.setDatabase(redissonProperties.getDatabase());
        return new DelayRedisson(config);
    }

}
