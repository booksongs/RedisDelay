package com.tiexiu.dq.config;

import org.redisson.Redisson;
import org.redisson.config.Config;

public class DelayRedisson extends Redisson {
    public DelayRedisson(Config config) {
        super(config);
    }
}
