/*
*
*@Date:2022年5月02日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.config;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ggl.cloud.entity.CommonResult;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:16:25
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public CommonResult error(Exception e){
        // e.printStackTrace();
        log.warn("clazz:"+e.getClass().getName());
        log.error("message:"+e.getMessage());
        String cause=e.getCause().getMessage();
        log.warn("cause:" + cause);
        return CommonResult.builder().code(CommonResult.ERROR).detail(e.getMessage()).build();
    }
}
