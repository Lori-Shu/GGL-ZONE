package com.ggl.cloud.utils;

import java.util.concurrent.TimeUnit;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.User;

import jakarta.servlet.http.HttpServletRequest;

public class SecurityRedisUtil {

  private static final Logger logger = LoggerFactory.getLogger(SecurityRedisUtil.class);

  public final static String HEADSTR = "token";
  public final static Long EXPIRE = 24L;
  public final static String USERNAMESTR = "username";
  public final static String AUTHSTR = "authorities";
  private final static String SECRET = """
  春江潮水连海平，海上明月共潮生。

  滟滟随波千万里，何处春江无月明！
  
  江流宛转绕芳甸，月照花林皆似霰；
  
  空里流霜不觉飞，汀上白沙看不见。
  
  江天一色无纤尘，皎皎空中孤月轮。
  
  江畔何人初见月？江月何年初照人？
  
  人生代代无穷已，江月年年望相似。
  
  不知江月待何人，但见长江送流水。
  
  白云一片去悠悠，青枫浦上不胜愁。
  
  谁家今夜扁舟子？何处相思明月楼？
  
  可怜楼上月裴回，应照离人妆镜台。
  
  玉户帘中卷不去，捣衣砧上拂还来。
  
  此时相望不相闻，愿逐月华流照君。
  
  鸿雁长飞光不度，鱼龙潜跃水成文。
  
  昨夜闲潭梦落花，可怜春半不还家。
  
  江水流春去欲尽，江潭落月复西斜。
  
  斜月沉沉藏海雾，碣石潇湘无限路。
  
  不知乘月几人归，落月摇情满江树""";

  public static String checkAuth(HttpServletRequest request, StringRedisTemplate stringRedisTemplate) {
      // 校验是否带有token
      String token = (String) request.getHeader(HEADSTR);
      if (!StringUtils.hasLength(token)) {
        throw new AccessDeniedException("Access Denied");
      }
      String user = stringRedisTemplate.opsForValue().get(token);
      if (user != null) {
        // 延长token有效期
        stringRedisTemplate.opsForValue().set(token, user, EXPIRE, TimeUnit.HOURS);
      return user;
    } else {
      return null;
    }
  }

  public static String createToken(UserDetails user, StringRedisTemplate redisTemplate, ObjectMapper om) {

    
    try {
      String username = user.getUsername();
    String token = DigestUtils.md5DigestAsHex((username + SECRET).getBytes());
    redisTemplate.opsForValue().set(token, om.writeValueAsString(user), EXPIRE, TimeUnit.HOURS);
    return token;
  } catch (JsonProcessingException e) {
    logger.warn("创建token出现json转换异常");
    throw new AccessDeniedException("Access Denied");
  }
    
  
  }
}

