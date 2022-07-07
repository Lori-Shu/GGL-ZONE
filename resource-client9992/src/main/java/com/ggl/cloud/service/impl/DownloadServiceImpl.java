package com.ggl.cloud.service.impl;

 import java.io.File;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.feignservice.ServerFeign;
import com.ggl.cloud.service.DownloadService;
import com.ggl.cloud.service.IResourceService;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
 /*
  *@Author Lori Shu
  *@Date 2022/4/23
  */
 @Service
 @Slf4j
 @Transactional
 public class DownloadServiceImpl implements DownloadService {
     @Resource
     private IResourceService service;
     @Resource
     private ServerFeign serverFeign;
    
     @Override
     public File downloadMusic(Music music) {
         log.warn("进入download...service");
         QueryWrapper<com.ggl.cloud.entity.Resource> queryWrapper = new QueryWrapper<>();
         queryWrapper.eq("resource_name", music.getMusicName());
         com.ggl.cloud.entity.Resource resource = new com.ggl.cloud.entity.Resource();
         resource.setRequestType("download");
         resource.setResourceName(music.getMusicName());
         resource.setResourceType("music");
         service.saveOrUpdate(resource, queryWrapper);
         serverFeign.plusDownload(music);
            return new File(music.getStorePath());
 //        throw new FileNotFoundException("文件未找到！！");
     }

    @Override
    public File downloadVideo(Video video) {
        // 下载视频资源的方法
        return new File(video.getStorePath());
    }
 }
