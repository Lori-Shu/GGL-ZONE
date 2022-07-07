/*
*
*@Date:2022年5月02日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.config;

import com.ggl.cloud.entity.CommonResult;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult error(Exception e){
        // e.printStackTrace();
        log.error(e.getMessage());
        return CommonResult.builder().code(CommonResult.ERROR).detail(e.getMessage()).build();
    }
}
