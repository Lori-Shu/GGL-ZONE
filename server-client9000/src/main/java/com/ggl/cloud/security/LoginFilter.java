package com.ggl.cloud.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
  private final ProviderManager providerManager;
  private final ObjectMapper om;
  private final String FAILRES = "登陆失败请检查";
  // 登录必须使用post请求
  private final String POST = "POST";

  public LoginFilter(ProviderManager providerManager,ObjectMapper objectMapper) {
    this.providerManager = providerManager;
    this.om = objectMapper;

    
  }
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
        // 校验是否已经过滤过了，因为注册为bean过滤器会在security过滤器链和默认过滤器链中都起作用
        Object isFiltered = request.getAttribute(getFilterName());
        if (isFiltered!=null&&(Boolean) isFiltered) {
          chain.doFilter(request, response);
          return;
        }
        //检查请求方式
        if (!((HttpServletRequest) request).getMethod().equals(POST)) {
          throw new AccessDeniedException("Access Denied");
        }
        try{
        User tempUser = om.readValue(request.getInputStream(), User.class);
        Authentication authenticate = providerManager.authenticate(
            UsernamePasswordAuthenticationToken.unauthenticated(tempUser.getUserId(), tempUser.getPassword()));
        if (authenticate.isAuthenticated()) {
          doAuthenticateSuccess((HttpServletResponse)response,authenticate);
        }
      } catch (Exception e) {
        doAuthenticateFail((HttpServletResponse)response);
      }

        
  }

  private void doAuthenticateFail(HttpServletResponse response) {
    try {
      ServletOutputStream outputStream = response.getOutputStream();
      om.writeValue(outputStream,FAILRES );
    } catch (IOException e) {
      log.warn("登陆失败返回过程出现异常"+e.getMessage());
      throw new AccessDeniedException("Access Denied");
    }
  }
  private void doAuthenticateSuccess(HttpServletResponse response, Authentication authenticate) {
    try {
      ServletOutputStream outputStream = response.getOutputStream();
      om.writeValue(outputStream, authenticate.getPrincipal());
    } catch (IOException e) {
      log.warn("登陆成功返回过程出现异常"+e.getMessage());
      throw new AccessDeniedException("Access Denied");
    }
  }
  
}
