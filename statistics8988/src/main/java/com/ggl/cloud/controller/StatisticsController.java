package com.ggl.cloud.controller;


import javax.annotation.Resource;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.service.IStatisticsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 统计分析表 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-05-17
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    @Resource
    private IStatisticsService service;
    @PostMapping("/selectPage")
    public CommonResult selectPage(int pageNumber,int pageSize,String from,String to){
        return service.selectPage(pageNumber, pageSize, from, to);
    } 

}
