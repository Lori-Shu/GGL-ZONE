package com.ggl.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Hello world!
 *
 */
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:36:19
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ResourceClient9992 {
    public static void main(String[] args){
        SpringApplication.run(ResourceClient9992.class,args);
    }
}
