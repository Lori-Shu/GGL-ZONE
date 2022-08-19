package com.ggl.cloud.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.utils.JwtUtil;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:31:17
 *
 */
public class TokenLogoutHandler implements LogoutHandler {
    private ObjectMapper om = new ObjectMapper();
    private RedisTemplate<String,Object> redisTemplate;

    public TokenLogoutHandler(RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String token = httpServletRequest.getHeader("token");
        if (token != null) {
            
            redisTemplate.delete(JwtUtil.getUserIdFromToken(token));
        }
        httpServletResponse.setStatus(HttpStatus.OK.value());
        httpServletResponse.setContentType("application/json;charset=utf-8");
        try {
            om.writeValue(httpServletResponse.getWriter(),CommonResult.builder().code(CommonResult.SUCCESS).detail("登出成功！").build());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
