package com.ggl.cloud.controller;

 import java.io.File;

import javax.annotation.Resource;

import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.service.DownloadService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

 /*
  *@Author Lori Shu
  *@Date 2022/4/23
  */
 @RestController
 @RequestMapping("resource/download")
 @Slf4j
 public class DownloadController {
     @Resource
     DownloadService downloadService;
     @PostMapping("music")
     public File download(@RequestBody Music music){
        log.warn("进入server/download"+"music"+music.getStorePath());
 //        log.info(src);
            return downloadService.downloadMusic(music);
     }
     @PostMapping("video")
     public File download(@RequestBody Video video){
 //        log.info("进入server/download");
 //        log.info(src);
            return downloadService.downloadVideo(video);
     }
 }
