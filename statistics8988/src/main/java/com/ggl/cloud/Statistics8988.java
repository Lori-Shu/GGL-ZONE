package com.ggl.cloud;

import java.util.HashMap;
import java.util.Map;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.service.IStatisticsService;
import com.ggl.cloud.service.feignservice.ServerFeign;

import jakarta.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:44:54
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@Slf4j
public class  Statistics8988{
    @Resource 
    private IStatisticsService service;
    @Resource
    private ServerFeign feign;
    public static void main(String[] args) {
        SpringApplication.run(Statistics8988.class, args);
    }
    @Scheduled(cron = "0 0 1 * * ?")
    @SuppressWarnings("unchecked")
    public void statisticsTask(){
        CommonResult count=feign.getMusicStatistics();
        log.warn(count.toString());
        ObjectMapper om=new ObjectMapper();
        Map<String,Integer> map=om.convertValue(count.getResult(), HashMap.class);
        CommonResult userResult=feign.getUserStatistics();
        Map<String,Integer> userMap=om.convertValue(userResult.getResult(), HashMap.class);
        map.putAll(userMap);
        service.insertStatistics(map);
    }
}