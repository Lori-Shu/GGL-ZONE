package com.ggl.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggl.cloud.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User>{

    int getRegistryCount(String date);
    
}
