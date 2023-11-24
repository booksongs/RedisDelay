package com.dp.example;

import com.tiexiu.dq.config.DelayRedisson;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    //支持自定义的redisson配置
    @Bean
    public DelayRedisson customRedissonDelayQueue() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer();
        //ping 5s
        singleServerConfig.setPingConnectionInterval(5000);
        singleServerConfig.setAddress("redis://" + "192.168.1.208" + ":32307");
//        singleServerConfig.setPassword();
        singleServerConfig.setKeepAlive(true);
        return new DelayRedisson(config);
    }
}
