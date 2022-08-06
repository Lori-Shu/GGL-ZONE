/*
*
*@Date:2022年5月05日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
    private static final long EXPIRE=1000*60*60*24;
    private static final String SECRET = "jsdoajflkasjflkssfdggsdfgsdffgfsdgsdfgtuhtryjd";
    private static final SecretKey MYKEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    public static String getJwtToken(String userId) {
        
        JwtBuilder tokenBuilder=Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS256")
            .setSubject("user")
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
            // .claim("id", id)
            .claim("userId", userId)
            .signWith(MYKEY,SignatureAlgorithm.HS256);

    return tokenBuilder.compact();

    }

    public static boolean checkToken(String token){
        if(StringUtils.isEmpty(token)) return false;
        try{
            Jwts.parser().setSigningKey(MYKEY).parseClaimsJws(token);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return true;


    }

    public static String getUserIdFromToken(String token){
        if(StringUtils.isEmpty(token)) return "";
        Jws<Claims> jwsClaims= Jwts.parser().setSigningKey(MYKEY).parseClaimsJws(token);
        Claims claims=jwsClaims.getBody();
        return String.valueOf(claims.get("userId"));

    }
    
}
