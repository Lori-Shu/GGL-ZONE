/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:20:07
 *
 */
public interface IMusicService extends IService<Music> {
    /**
     * 上传音乐
     * @param music
     * @return
     */
    CommonResult uploadMusic(Music music);
    /**
     * 删除音乐
     * @param music
     * @return
     */
    CommonResult deleteMusic(Music music);
    /**
     * 分页查询音乐
     * @param pageNumber
     * @param pageSize
     * @param music
     * @return
     */
    CommonResult selectMusicPage(int pageNumber,int pageSize,Music music);
    /**
     * 获得统计信息的方法
     * @return
     */
    CommonResult getStatistics();
    /**
     * 增加下载次数统计
     * @param music
     * @return
     */
    CommonResult plusDownload(Music music);
    
    
}
