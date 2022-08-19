/*
*
*@Date:2022年5月01日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:17:50
 *
 */
@Configuration
@Slf4j
public class MybatisPlusConfig {
    
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        /* 乐观锁必须先查再改才起效SQLFeatureNotSupportedException */
        log.info("插件加载完成");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

@Bean
public ObjectMapper objectMapper() {
    return new ObjectMapper();
}
}
