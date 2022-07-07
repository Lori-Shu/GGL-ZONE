package com.ggl.cloud.mapper;

import com.ggl.cloud.entity.Resource;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 资源表 Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-07-05
 */
@Mapper
public interface ResourceMapper extends BaseMapper<Resource> {

}
