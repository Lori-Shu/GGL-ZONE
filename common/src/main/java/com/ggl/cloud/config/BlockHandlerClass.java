/*
*
*@Date:2022年5月19日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.config;



import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Note;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class BlockHandlerClass  {
    public static CommonResult defaultBlock(Note note,BlockException exception){
        log.warn("触发服务降级---");
        return CommonResult.builder().code(CommonResult.ERROR).detail("服务繁忙请稍后重试！").build();
    }
}
