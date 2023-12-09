# RedisDelay
基于redisson的易于使用的延时队列,提供了类似mq的使用方式

## 快速使用
1. 引入
```
<dependency>
    <groupId>io.github.booksongs</groupId>
    <artifactId>rd-spring-boot-starter</artifactId>
    <version>最新版本</version>
</dependency>
```
2. 在yml增加你的redis配置

```  
  spring:
    redis:
      delay:
        host: 192.168.1.2
        port: 3306
        database: 1
```
3. 编写要发送的消息的实体类,需要继承Provider类,这个类会自动生成id和发送时间
4. 注入DelayTemplate使用offer方法,添加延时任务,需要指定topic
4. 使用@RedisListener注解标记你的方法,指定topic和你创建的继承Provider的类,就可以接收到指定的延时消息

## 其他功能

RedisListener 提供了一些属性
- containerFactory 指定容器使用的工厂,可以单独使用另外的配置,对应DelayRedissonUtil.offer,需要添加对应的bean

RedissonProperties配置类中有重试的开关,这个开关是全局的,RedisListener也提供了单独的设置,RedisListener的配置会覆盖RedissonProperties中的配置
