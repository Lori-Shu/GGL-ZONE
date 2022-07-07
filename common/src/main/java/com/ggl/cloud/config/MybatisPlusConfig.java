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
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class MybatisPlusConfig {
    // @Bean
    // public IKeyGenerator keyGenerator() {
    // return new H2KeyGenerator();
    // }
    @Bean
    // 乐观锁必须先查再改才起效SQLFeatureNotSupportedException
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        log.info("插件加载完成");
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    // @Bean
    // public PasswordEncoder passwordEncoder(){
    //     return new BCryptPasswordEncoder();
    // }
@Bean
public ObjectMapper objectMapper() {
    return new ObjectMapper();
}
}
