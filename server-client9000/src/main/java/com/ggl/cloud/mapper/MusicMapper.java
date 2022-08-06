/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggl.cloud.entity.Music;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MusicMapper extends BaseMapper<Music>{
    int getUploadCount(String date);

    int getDeleteCount(String date);
    
}
