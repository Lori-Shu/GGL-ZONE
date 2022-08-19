package com.ggl.cloud.controller;

 import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ggl.cloud.entity.Music;
import com.ggl.cloud.service.DownloadService;

import lombok.extern.slf4j.Slf4j;

 /**
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
     public byte[] download(@RequestBody Music music) throws IOException{
        log.warn("进入server/download"+"music"+music.getStorePath());
 //        log.info(src);
            return downloadService.downloadMusic(music);
     }
    //  @PostMapping("video")
    //  public Long download(@RequestBody Video video) {
    //      // 下载视频资源的方法
    //      log.warn("进入downloadservice...");
         
    //      //        log.info("进入server/download");
    //      //        log.info(src);

    //      downloadService.downloadVideo(video);
    //      log.warn("已开始发送数据");
    //      return new File(video.getStorePath()).length();
    //  }
    //  @PostMapping("testHS")
    //  public void testHS(@RequestBody Video video,HttpServletRequest request,HttpServletResponse response) {
    //      try (FileInputStream fis = new FileInputStream("C:\\Users\\shuguanglin\\Desktop\\Test.java");) {
    //          log.warn("进入测试方法");
    //          response.reset();
    //          response.setContentType("application/octet-stream");
    //         //  String path = video.getStorePath();
    //         //  int lI = path.lastIndexOf("/");
    //         //  String filename = path.substring(lI + 1);
    //          response.addHeader("Content-Disposition", "attachment;filename=" + UriUtils.encode("java", "UTF-8"));
    //          ServletOutputStream os = response.getOutputStream();
    //          byte[] bytes = new byte[1024];
    //          for (int readCount = 1; readCount > 0;) {
    //              readCount = fis.read(bytes);
    //              if (readCount > 0) {
    //                 os.write(bytes, 0 , readCount);
    //              }
    //          }
    //         log.warn("io结束");
    //     } catch (Exception e) {
    //         //io异常
    //         log.warn("io异常");
    //     }
    //  }
 }
