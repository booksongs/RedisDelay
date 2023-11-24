package com.dp.example;


import com.tiexiu.dq.annotation.EnableRedisDelay;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRedisDelay
public class DpExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(DpExampleApplication.class, args);
    }

}
