package com.admin.crawler;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(value = "com.admin.crawler.mapper")
public class MyTestApplication {

    public static void main(String[] args) {

        SpringApplication.run(MyTestApplication.class, args);


    }

}
