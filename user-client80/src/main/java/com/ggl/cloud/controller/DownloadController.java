package com.ggl.cloud.controller;


import java.io.File;

import javax.annotation.Resource;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.feignservice.ResourceFeign;

import lombok.extern.slf4j.Slf4j;

/*
 *@Author Lori Shu
 *@Date 2022/4/23
 */
@Controller
@RequestMapping("user/download")
@Slf4j
public class DownloadController {
    @Resource
    private    ResourceFeign service;
    @PostMapping("music")
    public ResponseEntity<FileSystemResource> downloadMusic(@RequestBody Music music){
        // log.info(src);
        try {
            File file = service.download(music);
            String fileName = file.getName();
            log.warn(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment;filename=\"%s", fileName));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Last-Modified", new java.util.Date().toString());
            headers.add("ETag", String.valueOf(System.currentTimeMillis()));

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new FileSystemResource(file));
        }catch (Exception e){
            e.printStackTrace();
        }
        log.error("出现问题");
        return null;
    }
    @PostMapping("video")
    public ResponseEntity<FileSystemResource> downloadVideo(@RequestBody Video video){
        // log.info(src);
        try {
            File file = service.download(video);
            String fileName = file.getName();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment;filename=\"%s", fileName));
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("Last-Modified", new java.util.Date().toString());
            headers.add("ETag", String.valueOf(System.currentTimeMillis()));

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new FileSystemResource(file));
        }catch (Exception e){
            e.printStackTrace();
        }
        log.error("出现问题");
        return null;
    }

}
