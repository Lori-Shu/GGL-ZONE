package com.ggl.cloud.controller;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.feignservice.ServerFeign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("user/music")
@Slf4j
public class MusicController {
    @Resource
    private ServerFeign service;
    private ObjectMapper om=new ObjectMapper();

    @PostMapping("upload")
    public CommonResult uploadMusic(MultipartFile uploadMusic, @RequestParam("music")String music) throws IOException{
//        if(uploadFile != null){
//            log.info("收到文件");
//        }else {
//            log.info("未收到文件");
//        }
    // log.warn(uploadMusic.getOriginalFilename());
    File file=File.createTempFile(uploadMusic.getOriginalFilename(), ".flac");
    uploadMusic.transferTo(file);
    
        return service.uploadmusic(file,music);
    }
    @GetMapping("delete")
    public CommonResult deleteMusic(@RequestBody Music music) {
//        if(uploadFile != null){
//            log.info("收到文件");
//        }else {
//            log.info("未收到文件");
//        }
        return service.delete(music);
    }

    @PostMapping("select_page")
    public CommonResult selectPage(int pageNumber, int pageSize,@RequestBody Music music) {
//        log.info(music);
ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
        log.warn("pageSize" + pageNumber);
        log.warn("pageSize"+pageSize);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        map.put("music", music);
        return service.selectPageMusic(map);
//        log.info(result.getPageList().toString());
    }

 


}
