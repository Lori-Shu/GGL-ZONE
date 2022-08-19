package com.ggl.cloud.security.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private RedisTemplate<String,Object> redisTemplate;
    private AuthenticationManager authenticationManager;
    // Springsecurity一系列过滤器无法访问到Spring的bean，所需要的对象必须自己创建
    private ObjectMapper om=new ObjectMapper();


    public TokenLoginFilter(AuthenticationManager authenticationManager, RedisTemplate<String,Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.setPostOnly(true);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/server/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.warn("进入登录方法！");
        try {
            
            // log.warn(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
            // ServletInputStream is = request.getInputStream();
            // log.warn(is.available()+"");
            // byte[] bytes=new byte[is.available()];
            // is.read(bytes,0,is.available());
            // log.warn(new String(bytes, "UTF-8"));
            // log.warn(om.getClass().getName());
            User user = om.readValue(request.getInputStream(), User.class);

        //     StringBuffer data = new StringBuffer();
        // String line;
        // BufferedReader reader;
        // try {
        //     reader = request.getReader();
        //     while (null != (line = reader.readLine())) {
        //         // log.warn(line);
        //         data.append(line);
        //     }
        // } catch (IOException e) {
        //     log.warn(e.getMessage());
        // }

//            setUsernameParameter("userId");
            log.warn(user.toString());
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserId(), user.getPassword(),new ArrayList<>()));
        } catch (Exception e) {
            log.warn(e.getMessage());
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=utf-8");
            try {
                om.writeValue(response.getWriter(),CommonResult.builder().code(CommonResult.ERROR).detail("认证失败!").build());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        try {
            log.warn(user.toString());
            String token = JwtUtil.getJwtToken(user.getUsername());
            ArrayList<String> list = new ArrayList<>();
            for (GrantedAuthority g : user.getAuthorities()) {
                list.add(g.getAuthority());
            }
            redisTemplate.opsForValue().set(user.getUsername(), list.toString());
            response.setStatus(HttpStatus.OK.value());
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("token", token);
            response.setHeader("Access-Control-Expose-Headers", "token");
            om.writeValue(response.getWriter(),CommonResult.builder().code(CommonResult.SUCCESS).detail("认证成功！").build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        failed.printStackTrace();
        throw new UsernameNotFoundException("认证失败！");
    }
}
