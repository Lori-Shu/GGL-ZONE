package com.ggl.cloud.config;


import java.util.function.Supplier;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.security.GglAuthorizationFilter;
import com.ggl.cloud.utils.SecurityRedisUtil;

import jakarta.servlet.http.HttpServletRequest;
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
  public AuthorizationManager<HttpServletRequest> authorizationManager(StringRedisTemplate stringRedisTemplate,
      ObjectMapper objectMapper) {
    final ObjectMapper om = objectMapper;
    return new AuthorizationManager<>() {

      @Override
      @Nullable
      public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest object) {
        try{
        String userStr = SecurityRedisUtil.checkAuth(object, stringRedisTemplate);
        if (StringUtils.hasLength(userStr)) {
          User user = om.readValue(userStr, User.class);
          // 设置权限
        SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
        SecurityContext context = securityContextHolderStrategy.getContext();
          context.setAuthentication(UsernamePasswordAuthenticationToken.authenticated(user.getUserId(), null,
              AuthorityUtils.createAuthorityList(user.getAuth())));
              return new AuthorizationDecision(true);
        } else {
          return new AuthorizationDecision(false);
        }
        } catch (Exception e) {
          log.warn("授权过程出现异常" + e.getMessage());
          return new AuthorizationDecision(false);
          // throw new AccessDeniedException("Access Denied");
      }
      }

    };

  }
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,AuthorizationManager<HttpServletRequest> authorizationManager) {
    try {
      HttpSecurity httpS = httpSecurity.csrf().disable()
          .cors().and()
          .authorizeHttpRequests(authorize -> {
            authorize.requestMatchers("/resource/**", "/user/note/sayhi").permitAll()
                .requestMatchers("/gglLogin").permitAll()
                .anyRequest().authenticated();
          });
      // String authenticationFilter="authenticationFilter";
      // Object bean = applicationContext.getBean(authenticationFilter);
      // if(bean!=null){
      //   httpS.addFilterAfter((AuthenticationFilter)bean, AuthorizationFilter.class);
      // }
      return httpS.build();
    } catch (Exception e) {
      log.warn("创建FilterChain出现异常"+e.getMessage());
      return null;
    }
  }
}
