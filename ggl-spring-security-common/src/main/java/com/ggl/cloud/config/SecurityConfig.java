package com.ggl.cloud.config;


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
  ObjectMapper objectMapper) {
    try {
      HttpSecurity httpS = httpSecurity.csrf().disable()
          .cors().and()
          .authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/resource/**", "/favicon.ico").permitAll()
                .requestMatchers("/gglLogin","/login").permitAll()
                .anyRequest().access(new GglAuthorizationManager(stringRedisTemplate, objectMapper));
          });
      // String authenticationFilter="authenticationFilter";
      // Object bean = applicationContext.getBean(authenticationFilter);
      // if(bean!=null){
      //   httpS.addFilterAfter((AuthenticationFilter)bean, AuthorizationFilter.class);
      // }
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
