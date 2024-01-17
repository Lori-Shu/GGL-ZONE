package com.ggl.cloud.config;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.HeaderWriterFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.security.GglAuthorizationManager;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,StringRedisTemplate stringRedisTemplate,
  ObjectMapper objectMapper,ApplicationContext applicationContext) {
    try {
      HttpSecurity httpS = httpSecurity.csrf().disable()
          .cors().and()
          .authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/server/user/registry").permitAll()
            .requestMatchers("/actuator/health").permitAll()
            .requestMatchers("/favicon.ico").permitAll()
            .requestMatchers("/resource/**").permitAll()
                .anyRequest().access(new GglAuthorizationManager(stringRedisTemplate, objectMapper));
          });
          try {
            String authenticationFilterClazz="com.ggl.cloud.security.LoginFilter";
            Class<?> clazz = Class.forName(authenticationFilterClazz);
            String providerManager="providerManager";
            Object providerObject = applicationContext.getBean(providerManager);
            if(clazz!=null){
              httpS.addFilter((UsernamePasswordAuthenticationFilter) (clazz.getConstructors()[0].newInstance(providerObject,objectMapper)));
            }
          } catch (Exception e) {
            log.warn("未找到对应bean未创建LoginFilter");
            // 未查找到bean则啥也不做
          }
      
      httpS.exceptionHandling(exceptionHandlingCustomizer -> {
        exceptionHandlingCustomizer.authenticationEntryPoint((req, res, exception) -> {
          log.warn(exception.getMessage());
          res.getWriter().write(exception.getMessage());
          res.getWriter().flush();
        });
        exceptionHandlingCustomizer.accessDeniedHandler((req, res, exception) -> {
          log.warn(exception.getMessage());
          res.getWriter().write(exception.getMessage());
          res.getWriter().flush();
        });
      });
      return httpS.build();
    } catch (Exception e) {
      log.warn("创建FilterChain出现异常"+e.getMessage());
      return null;
    }
  }
}
