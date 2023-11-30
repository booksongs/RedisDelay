package com.tiexiu.dq.confirm;

import com.tiexiu.dq.config.DelayRedisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RList;

public class ConfirmUtil {

    public final static String RETRY = "RETRY_";

    public static RAtomicLong getAtomicLong(String key, DelayRedisson delayRedisson) {
        return delayRedisson.getAtomicLong(key);
    }

    public static void incrementAndGet(String key, DelayRedisson delayRedisson) {
        getAtomicLong(key, delayRedisson).incrementAndGet();
    }

    public static void del(String key, DelayRedisson delayRedisson) {
        getAtomicLong(key, delayRedisson).delete();
    }

    public static void addDeadLetterQueue(String key, DelayRedisson delayRedisson, DeadLetterProvider message) {
        RList<DeadLetterProvider> list = delayRedisson.getList(key);
        list.add(message);
    }
}
