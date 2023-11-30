# RedisDelay
基于redisson的易于使用的延时队列
## 使用方式
1. 在yml增加你的redis配置


```  
  spring:
    redis:
      delay:
        host: 
        port: 
        database: 
```

2. 在启动类上增加注解@EnableRedisDelay开启使用RedisDelay
3. 注入DelayRedisson类,它是派生Redisson的类,可以使用RedissonDelayedQueue.offer方法,添加延时任务,需要指定topic
4. 使用@RedisDelayQueue注解标记你的方法,指定topic,就可以接收到指定的延时消息
