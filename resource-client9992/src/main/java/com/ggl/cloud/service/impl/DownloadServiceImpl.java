package com.ggl.cloud.service.impl;

 import java.io.File;

import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.service.DownloadService;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
 /*
  *@Author Lori Shu
  *@Date 2022/4/23
  */
 @Service
 @Slf4j
 public class DownloadServiceImpl implements DownloadService {
     @Override
     public File downloadMusic(Music music) {
         log.warn("进入download...service");
            return new File(music.getStorePath());
 //        throw new FileNotFoundException("文件未找到！！");
     }

    @Override
    public File downloadVideo(Video video) {
        // TODO Auto-generated method stub
        return new File(video.getStorePath());
    }
 }
