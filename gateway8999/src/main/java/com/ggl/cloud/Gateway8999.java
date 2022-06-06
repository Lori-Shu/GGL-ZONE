package com.ggl.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient

public class Gateway8999 {
    public static void main(String[] args) {
        SpringApplication.run(Gateway8999.class, args);
    }
}
