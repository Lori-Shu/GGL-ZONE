package com.ggl.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggl.cloud.entity.User;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:17:22
 *
 */
@Mapper
public interface UserMapper extends BaseMapper<User>{
    /**
     * 获得指定时间之后注册的人数
     * @param date
     * @return
     */
    int getRegistryCount(String date);
    
}
