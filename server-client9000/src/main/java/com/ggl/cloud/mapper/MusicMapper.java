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
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:15:33
 *
 */
@Mapper
public interface MusicMapper extends BaseMapper<Music> {
    /**
     * 获得指定时间之后上传音乐的数量
     * @param date
     * @return
     */
    int getUploadCount(String date);
    /**
     * 获得指定时间之后删除音乐的数量
     * @param date
     * @return
     */
    int getDeleteCount(String date);
    
}
