/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggl.cloud.config.BlockHandlerClass;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.mapper.VideoMapper;
import com.ggl.cloud.service.IVideoService;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class VideoServiceImpl extends ServiceImpl<VideoMapper,Video> implements IVideoService{
    @Override
    @CachePut(value = "uploadVideo",key = "#video.userId")
    @SentinelResource(value = "uploadVideo",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult uploadVideo(Video video){
            if(save(video)){
                return CommonResult.builder().code(CommonResult.SUCCESS).detail("上传视频成功！").build();
            }
            throw new RuntimeException("保存视频记录出现问题");
    }
        @Override
        @CacheEvict(value = "selectPageVideo",key = "#video.userId")
        @SentinelResource(value = "deleteVideo",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult deleteVideo(Video video) {
        if(removeById(video)){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除视频成功").build();
        }
        throw new RuntimeException("删除视频记录出现异常！");
    }
        @Override
        @Cacheable(value = "selectPageVideo",key = "#video.userId")
        public CommonResult selectVideoPage(int pageNumber, int pageSize, Video video) {
            // 分页查询视频信息
            Page<Video> videoPage=new Page<>(pageNumber,pageSize);
        QueryWrapper<Video> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isEmpty(video.getUserId())){
            queryWrapper.like("user_id", video.getUserId());
        }
        if(!StringUtils.isEmpty(video.getVideoName())){
            queryWrapper.like("video_name", video.getVideoName());
        }
        if(!StringUtils.isEmpty(video.getVideoAuthor())){
            queryWrapper.like("video_author", video.getVideoAuthor());
        }
        page(videoPage, queryWrapper);
        if(videoPage.getRecords().size()>0){
            return CommonResult.builder()
                .code(CommonResult.SUCCESS)
                .detail("查询视频页面成功")
                .result(videoPage.getRecords())
                .build();
        }
        throw new RuntimeException("查询视频页面出现问题，结果为空！");
        }
    
}
