package com.ggl.cloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:05:21
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServerClient9000 {
    public static void main(String[] args) {
        SpringApplication.run(ServerClient9000.class, args);
    }
}
