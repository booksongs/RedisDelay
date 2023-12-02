package io.github.booksongs.rd.example.config;

import io.github.booksongs.rd.config.DelayRedisson;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    /**
     * 支持为每个监听器指定不同的DelayRedisson实例
     * 这里我们可以自定义DelayRedisson配置,需要在注解 @RedisListener( containerFactory = "customRedissonDelayQueue") 显示的指定
     * @return
     */
    @Bean
    public DelayRedisson customRedissonDelayQueue() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        //ping 5s
        singleServerConfig.setPingConnectionInterval(5000);
        singleServerConfig.setAddress("redis://" + "192.168.1.221" + ":16379");
//        singleServerConfig.setPassword();
        singleServerConfig.setKeepAlive(true);
        return new DelayRedisson(config);
    }

    /**
     * 默认的 DelayRedisson实例,会自动初始化
     * @param redissonProperties
     * @return
     */
//    @Bean
//    public DelayRedisson delayRedisson(RedissonProperties redissonProperties) {
//        Config config = new Config();
//        SingleServerConfig singleServerConfig = config.useSingleServer();
//        //ping 5s
//        singleServerConfig.setPingConnectionInterval(5000);
//        singleServerConfig.setAddress("redis://" + redissonProperties.getHost() + ":" + redissonProperties.getPort());
//        singleServerConfig.setPassword(redissonProperties.getPassword());
//        singleServerConfig.setKeepAlive(true);
//        singleServerConfig.setDatabase(redissonProperties.getDatabase());
//        return new DelayRedisson(config);
//    }
}
