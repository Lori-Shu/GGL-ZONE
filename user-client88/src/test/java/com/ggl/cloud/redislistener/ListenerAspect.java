package com.ggl.cloud.redislistener;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 没有注册到spring容器里作为bean的对象切面无法生效
 *
 * @author lori
 * createTime 2022年9月07日-17:21:48
 */
@Aspect
@Component
@Slf4j
public class ListenerAspect{
    @Around("execution(* com.ggl.cloud.redislistener.MyRedisListener.*(..))")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint){
        log.warn("进入监听器");
        Object res;
        try {
            res = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            log.warn("监听方法结束");
            return res;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
        return null;
    } 
}
