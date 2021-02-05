package com.wzh.fruitgoserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wzh.fruitgoserver.Dao")
@SpringBootApplication
public class FruitgoserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(FruitgoserverApplication.class, args);
    }

}
