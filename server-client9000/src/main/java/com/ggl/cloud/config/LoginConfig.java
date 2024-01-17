package com.ggl.cloud.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.security.LoginFilter;
import com.ggl.cloud.security.MyDaoAuthenticationProvider;
import com.ggl.cloud.service.impl.UserServiceImpl;
import com.ggl.cloud.utils.SecurityRedisUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
public class LoginConfig {
  
  @Bean
  public ProviderManager providerManager(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder,StringRedisTemplate stringRedisTemplate,
  ObjectMapper objectMapper) {
    return new ProviderManager(new MyDaoAuthenticationProvider(userDetailsService, passwordEncoder, stringRedisTemplate, objectMapper));
  }
}
