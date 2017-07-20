package com.didichuxing.janusgraph;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhaobing
 */
@SpringBootApplication
@EnableScheduling
public class SpringbootSdnApplication {

    public static void main(String[] args){
        SpringApplication.run(SpringbootSdnApplication.class, args);
    }
}
