package com.xiaxia.mallchat.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaxia.mallchat.common.**.mapper")
public class  ChatLeariningApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatLeariningApplication.class, args);
    }

}
