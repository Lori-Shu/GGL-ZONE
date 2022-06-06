package com.ggl.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Video;

public interface IVideoService extends IService<Video>{
    CommonResult uploadVideo(Video video);
    CommonResult deleteVideo(Video video);
    CommonResult selectVideoPage(int pageNumber,int pageSize,Video video);

}