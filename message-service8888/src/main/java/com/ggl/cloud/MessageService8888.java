package com.ggl.cloud;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:32:57
 *
 */
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,DruidDataSourceAutoConfigure.class})
@EnableDiscoveryClient
public class MessageService8888 {

	public static void main(String[] args) {
		SpringApplication.run(MessageService8888.class, args);
	}

}
