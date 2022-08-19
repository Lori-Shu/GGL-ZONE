package com.ggl.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Video;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:26:43
 *
 */
public interface IVideoService extends IService<Video> {
    /**
     * upload
     * @param video
     * @return
     */
    CommonResult uploadVideo(Video video);
    /**
     * delete
     * @param video
     * @return
     */
    CommonResult deleteVideo(Video video);
    /**
     * select
     * @param pageNumber
     * @param pageSize
     * @param video
     * @return
     */
    CommonResult selectVideoPage(int pageNumber,int pageSize,Video video);

}