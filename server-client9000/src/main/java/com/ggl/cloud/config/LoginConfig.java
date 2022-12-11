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
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.utils.SecurityRedisUtil;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoginConfig {
  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder,StringRedisTemplate stringRedisTemplate,
  ObjectMapper objectMapper) {
    final UserDetailsService service = userDetailsService;
    final PasswordEncoder encoder = passwordEncoder;
    final StringRedisTemplate redisTemplate = stringRedisTemplate;
    final ObjectMapper om = objectMapper;
    return new DaoAuthenticationProvider(){

      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
          String username = (String) authentication.getPrincipal();
          String password = (String) authentication.getCredentials();
          if (StringUtils.hasLength(username) && StringUtils.hasLength(password)) {
            UserDetails user = service.loadUserByUsername(username);
            if (user == null) {
              throw new AccessDeniedException("Access Denied");
            }
            boolean matches = encoder.matches(password, user.getPassword());
            if (!matches) {
              throw new AccessDeniedException("Access Denied");
            }
            String token = SecurityRedisUtil.createToken(user, redisTemplate,om);
            return UsernamePasswordAuthenticationToken.authenticated(token, null, null);

          } else {
            throw new AccessDeniedException("Access Denied");
          }
        } catch (AccessDeniedException e) {
          throw new AccessDeniedException("Access Denied");
        }
        catch (Exception e) {
          log.warn("登录过程出现异常"+e.getMessage());
          throw new AccessDeniedException("Access Denied");
        }
        
      }
      
    };
  }
  @Bean
  public AuthenticationManager authenticationManager(DaoAuthenticationProvider authenticationProvider) {
    return new ProviderManager(authenticationProvider);
  }
}
