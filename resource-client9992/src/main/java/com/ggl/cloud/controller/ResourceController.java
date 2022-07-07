/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.feignservice.ServerFeign;
import com.ggl.cloud.service.IResourceService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("resource")
@Slf4j
public class ResourceController {
    @Resource
    private IResourceService service;
    private ObjectMapper om = new ObjectMapper();

    @Resource
    private ServerFeign serverFeign;
    @PostMapping("uploadMusic")
    public CommonResult uploadMusic(@RequestBody byte[] uploadMusic,@RequestParam("music") String music,String suffix) throws JsonProcessingException, FileNotFoundException{
        Music resolveMusic=om.readValue(music, Music.class);
        return service.uploadMusic(uploadMusic,resolveMusic,suffix);

    }
    @PostMapping("deleteMusic")
    public CommonResult deleteMusic(@RequestBody Music music) {
        log.warn(music.toString());
        return service.deleteMusic(music);

    }
    @PostMapping("uploadVideo")
    public CommonResult uploadVideo(@RequestBody byte[] uploadVideo,@RequestParam("video")String video,String suffix) throws JsonProcessingException, FileNotFoundException{
        Video resolvedVideo=om.readValue(video, Video.class);
        return service.uploadVideo(uploadVideo,resolvedVideo,suffix);

    }
    @PostMapping("deleteVideo")
    public CommonResult deleteVideo(@RequestBody Video video) {
        return service.deleteVideo(video);

    }
    @PostMapping("uploadAvatar")
    public CommonResult uploadAvatar(@RequestBody byte[] avatar, String userId,String suffix) {
        String avatarSrc= service.uploadAvatar(avatar,userId,suffix);
        if (avatarSrc != null) {
            User user = new User();
            user.setUserId(userId);
            user.setAvatar(avatarSrc);
            return serverFeign.uploadAvatar(user);
        }
        throw new RuntimeException("存储头像过程出现问题");
    }
}
