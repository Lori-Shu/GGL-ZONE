package com.ggl.cloud.security;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.utils.SecurityRedisUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class MyDaoAuthenticationProvider extends DaoAuthenticationProvider{
  private UserDetailsService userDetailsService;
  private PasswordEncoder passwordEncoder;
  private StringRedisTemplate stringRedisTemplate;
  private ObjectMapper objectMapper;
  public MyDaoAuthenticationProvider(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder,StringRedisTemplate stringRedisTemplate,
  ObjectMapper objectMapper){
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.stringRedisTemplate = stringRedisTemplate;
    this.objectMapper = objectMapper;
  }
  @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
          String username = (String) authentication.getPrincipal();
          String password = (String) authentication.getCredentials();
          if (StringUtils.hasLength(username) && StringUtils.hasLength(password)) {
            UserDetails user = userDetailsService.loadUserByUsername(username);
            if (user == null) {
              throw new AccessDeniedException("Access Denied");
            }
            boolean matches = passwordEncoder.matches(password, user.getPassword());
            if (!matches) {
              throw new AccessDeniedException("Access Denied");
            }
            String token = SecurityRedisUtil.createToken(user, stringRedisTemplate,objectMapper);
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
      
    }
