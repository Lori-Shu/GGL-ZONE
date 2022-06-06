package com.ggl.cloud.service;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Statistics;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 统计分析表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-17
 */
public interface IStatisticsService extends IService<Statistics> {

    CommonResult insertStatistics(Map<String,Integer> result);
    CommonResult selectPage(int pageNumber,int pageSize,String from,String to);

}
