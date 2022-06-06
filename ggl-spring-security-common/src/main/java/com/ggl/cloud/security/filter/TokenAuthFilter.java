package com.ggl.cloud.security.filter;


import com.ggl.cloud.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class TokenAuthFilter extends BasicAuthenticationFilter {
    private RedisTemplate<String,Object> redisTemplate;

    public TokenAuthFilter(AuthenticationManager authenticationManager, RedisTemplate<String,Object> redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        if(request.getMethod().equals("OPTIONS")){
//            return;
//        }
        UsernamePasswordAuthenticationToken authRequest = getAuthentication(request);
        if (authRequest != null) {
            SecurityContextHolder.getContext().setAuthentication(authRequest);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while(headerNames.hasMoreElements()){
//            System.out.println(headerNames.nextElement());
//        }
        String token = request.getHeader("token");
        if (token == null) {
            token = request.getParameter("token");
            log.info("wsToken======" + token);
        }
        if (token != null&&JwtUtil.checkToken(token)) {
            String username = JwtUtil.getUserIdFromToken(token);
//        List<String> auths=  (List<String>) redisTemplate.opsForValue().get(username);
            // ArrayList<GrantedAuthority> auths = new ArrayList<>();
            String s = (String) redisTemplate.opsForValue().get(username);
            // String substring = s.substring(1, s.length() - 1);
            // for (String s1 : substring.split(",")) {
            //     log.info(s1);
            //     List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(s1);
            //     auths.add(grantedAuthorities.get(0));
            // }
            List<GrantedAuthority> grantedAuthority=AuthorityUtils.commaSeparatedStringToAuthorityList(s);
//            System.out.println(request.getParameter("userId"));
//            System.out.println(request.getRequestURL());
//        for (String s:auths){
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(s);
//            authList.add(simpleGrantedAuthority);
//        }
            return new UsernamePasswordAuthenticationToken(username, token, grantedAuthority);
        }
        return null;
    }
}
