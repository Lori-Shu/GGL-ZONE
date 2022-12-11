package com.ggl.cloud.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ggl.cloud.utils.SecurityRedisUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class GglAuthorizationFilter extends AuthorizationFilter {
  
  private final AuthorizationManager<HttpServletRequest> authorizationManager;

  

  public GglAuthorizationFilter(AuthorizationManager<HttpServletRequest> authorizationManager) {
    super(authorizationManager);
    this.authorizationManager = authorizationManager;
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
      throws ServletException, IOException {
        // 校验是否已经过滤过了，因为注册为bean过滤器会在security过滤器链和默认过滤器链中都起作用
    Object isFiltered = servletRequest.getAttribute(getFilterName());
    if ((Boolean) isFiltered) {
      chain.doFilter(servletRequest, servletResponse);
      return;
    }
    AuthorizationDecision decision = authorizationManager.check(null, (HttpServletRequest) servletRequest);
    if (decision.isGranted()) {
      chain.doFilter(servletRequest, servletResponse);
    } else {
      throw new AccessDeniedException("Access Denied");
    }
  }

  
  
}
