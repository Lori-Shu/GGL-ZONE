
package com.ggl.cloud;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月18日-下午9:28:52
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableDiscoveryClient
@EnableFeignClients

public class UserClient80 {
    public static void main(String[] args) {
        new String();
        SpringApplication.run(UserClient80.class, args);
    }
}
