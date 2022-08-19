package com.ggl.cloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Statistics;
import com.ggl.cloud.mapper.StatisticsMapper;
import com.ggl.cloud.service.IStatisticsService;

import org.springframework.stereotype.Service;



/**
 * <p>
 * 统计分析表 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-17
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements IStatisticsService {
    @Resource
    private StatisticsMapper mapper;

    @Override
    public CommonResult insertStatistics(Map<String, Integer> result) {
        // 插入统计数据方法
        Statistics statistics = new Statistics();
        log.warn("得到的统计结果---" + result.get("musicUploadCount"));
        statistics.setMusicUploadCount(result.get("musicUploadCount"));
        statistics.setMusicDeleteCount(result.get("musicDeleteCount"));
        statistics.setUserRegistryCount(result.get("userRegistryCount"));
        if (save(statistics)) {
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("统计数据记录成功").build();
        }
        throw new RuntimeException("统计数据记录失败");
    }

    @Override
    public CommonResult selectPage(int pageNumber, int pageSize, String from,String to) {
        // 分页查询统计数据方法
        int startIndex=(pageNumber-1)*pageSize+1;
        List<Statistics> resultList=mapper.mySelectPage(startIndex,pageSize,from,to);
        return CommonResult.builder().code(200).detail("查询统计数据成功").result(resultList).build();
    }

}
