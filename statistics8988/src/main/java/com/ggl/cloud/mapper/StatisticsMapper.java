package com.ggl.cloud.mapper;

import com.ggl.cloud.entity.Statistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 统计分析表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-05-17
 */
@Mapper
public interface StatisticsMapper extends BaseMapper<Statistics> {
    List<Statistics> mySelectPage(@Param("startIndex") int startIndex,@Param("pageSize") int pageSize,@Param("from") String from,@Param("to") String to);
}
