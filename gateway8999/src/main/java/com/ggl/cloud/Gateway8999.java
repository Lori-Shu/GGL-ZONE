package com.ggl.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:25:43
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAsync
public class Gateway8999 {
    public static void main(String[] args) {
        SpringApplication.run(Gateway8999.class, args);
    }
}
