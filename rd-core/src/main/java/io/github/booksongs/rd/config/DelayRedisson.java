package io.github.booksongs.rd.config;

import org.redisson.Redisson;
import org.redisson.config.Config;

public class DelayRedisson extends Redisson {
    public DelayRedisson(Config config) {
        super(config);
    }
}
