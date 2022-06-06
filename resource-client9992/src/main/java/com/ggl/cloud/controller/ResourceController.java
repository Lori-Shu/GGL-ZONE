/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.controller;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.service.ResourceService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("resource")
public class ResourceController {
    @Resource
    private ResourceService service;
    private ObjectMapper om=new ObjectMapper();
    @PostMapping("uploadMusic")
    public CommonResult uploadMusic(@RequestBody File uploadMusic,@RequestParam("music") String music,HttpServletRequest request) throws JsonProcessingException{
        Music resolveMusic=om.readValue(music, Music.class);
        return service.uploadMusic(uploadMusic,resolveMusic,request);

    }
    @PostMapping("deleteMusic")
    public CommonResult deleteMusic(@RequestBody Music music){
        return service.deleteMusic(music);

    }
    @PostMapping("uploadVideo")
    public CommonResult uploadVideo(@RequestBody File uploadVideo,@RequestParam("video")String video, HttpServletRequest request) throws JsonProcessingException{
        Video resolvedVideo=om.readValue(video, Video.class);
        return service.uploadVideo(uploadVideo,resolvedVideo,request);

    }
    @PostMapping("deleteVideo")
    public CommonResult deleteVideo(@RequestBody Video video){
        return service.deleteVideo(video);

    }
}
