package com.ggl.cloud.annotationaop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author lori
 * @createTime 2022年9月30日-22:43:47
 */
// 这种方式无法实现没有实现类的mapper型aop
// @Aspect
// @Component
@Slf4j
public class MyComponentAspect {
    @Around("execution(* com.ggl.cloud.AnnotationAop.MyInterface.*(..))")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint){
        log.warn("进入监听器");
        Object res;
        try {
            Class<?> clazz = proceedingJoinPoint.getTarget().getClass();
            log.warn("方法所在类的注解名：{}",clazz.getAnnotations()[0].toString());
            res = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            log.warn("监听方法结束");
            return res;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        
        return null;
    } 
}
