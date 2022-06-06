package com.ggl.cloud.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.utils.JwtUtil;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenLogoutHandler implements LogoutHandler {
    private RedisTemplate<String,Object> RedisTemplate;

    public TokenLogoutHandler(RedisTemplate<String,Object> RedisTemplate) {
        this.RedisTemplate = RedisTemplate;
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String token = httpServletRequest.getHeader("token");
        if (token != null) {
            
            RedisTemplate.delete(JwtUtil.getUserIdFromToken(token));
        }
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(httpServletResponse.getWriter(),CommonResult.builder().code(CommonResult.SUCCESS).detail("登出成功！").build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
