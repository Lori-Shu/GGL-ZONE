package com.ggl.cloud.controller;


import java.io.IOException;
import java.util.HashMap;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.feignservice.ServerFeign;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:47:47
 *
 */
@RestController
@RequestMapping("user/music")
@Slf4j
public class MusicController {
    @Resource
    private ServerFeign service;


    @PostMapping("upload")
    public CommonResult uploadMusic(MultipartFile uploadMusic, @RequestParam("music")String music) throws IOException{
//        if(uploadFile != null){
//            log.info("收到文件");
//        }else {
//            log.info("未收到文件");
//        }
    log.warn(music);
    String originalFilename = uploadMusic.getOriginalFilename();
    int index = originalFilename.lastIndexOf(".");
    String suffix = originalFilename.substring(index);
        return service.uploadmusic(uploadMusic.getBytes(),music,suffix);
    }
    @PostMapping("delete")
    public CommonResult deleteMusic(@RequestBody Music music) {
        //        if(uploadFile != null){
        //            log.info("收到文件");
        //        }else {
        //            log.info("未收到文件");
        //        }
        log.warn(music.toString());
        return service.delete(music);
    }

    @PostMapping("select_page")
    public CommonResult selectPage(int pageNumber, int pageSize,@RequestBody Music music) {
//        log.info(music);
        HashMap<String, Object> map = new HashMap<>(3);
        log.warn("pageSize" + pageNumber);
        log.warn("pageSize"+pageSize);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        map.put("music", music);
        return service.selectPageMusic(map);
//        log.info(result.getPageList().toString());
    }

 


}
