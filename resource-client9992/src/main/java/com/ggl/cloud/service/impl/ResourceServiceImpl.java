/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service.impl;


import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.service.ResourceService;
import com.ggl.cloud.utils.StoreUtil;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class ResourceServiceImpl implements ResourceService {
    @Override
    public String getHalfSrcFromRequest(HttpServletRequest request) {
        // TODO Auto-generated method stub
        StringBuffer url=request.getRequestURL();
        int index=url.indexOf("resource");
        String halfSrc=url.substring(0, index);
        return halfSrc;
    }
    @Override
    public String getDirPathFromMyuri(String myUri) {
// TODO Auto-generated method stub
        return "E://GGL-ZONE-resources/"+myUri;
    }

    @Override
    public CommonResult uploadMusic(File uploadMusic,Music music,HttpServletRequest request) throws JsonProcessingException  {
        // TODO Auto-generated method stub
        String halfSrc=getHalfSrcFromRequest(request);
        StringBuffer myUri=new StringBuffer();
        myUri.append("music");
        myUri.append("/"+music.getMusician()+"/");
        String dirPath=getDirPathFromMyuri(myUri.toString());
        String musicPath=dirPath+music.getMusicName()+".flac";
        music.setSrc(halfSrc+myUri.toString()+music.getMusicName()+".flac");
        music.setStorePath(musicPath);
//        log.info(realPath);
            File dir = new File(dirPath);
            File fileToStore = new File(musicPath);
            if (!dir.isDirectory()) {
                if (dir.mkdirs()) {
                    if("储存成功".equals(StoreUtil.storeFile(uploadMusic, fileToStore))){
                        return CommonResult.builder()
                            .code(CommonResult.SUCCESS)
                            .detail("储存成功")
                            .result(music)
                            .build();
                    }
                    throw new RuntimeException("储存音乐出现问题");
                }
                throw new RuntimeException("创建文件夹出现异常!");
            }
            if (fileToStore.isFile()) {
                throw new RuntimeException("文件已存在");
            }
            if("储存成功".equals(StoreUtil.storeFile(uploadMusic, fileToStore))){
                return CommonResult.builder()
                    .code(CommonResult.SUCCESS)
                    .detail("储存成功")
                    .result(music)
                    .build();
            }
            throw new RuntimeException("储存音乐出现问题");
    }

    @Override
    public CommonResult deleteMusic(Music music) {
        // TODO Auto-generated method stub
        if(StoreUtil.deleteFile(music.getStorePath()).equals("删除成功")){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除音乐成功！").build();
        }
        throw new RuntimeException("删除音乐文件发生异常！");
    }

    @Override
    public CommonResult uploadVideo(File uploadVideo,Video video, HttpServletRequest request) throws JsonProcessingException {
        // TODO Auto-generated method stub
        String halfSrc=getHalfSrcFromRequest(request);
        StringBuffer myUri=new StringBuffer();
        myUri.append("video");
        myUri.append("/"+video.getVideoAuthor()+"/");
        String dirPath=getDirPathFromMyuri(myUri.toString());
        String videoPath=dirPath+video.getVideoName()+".mp4";
        video.setSrc(halfSrc+myUri.toString()+video.getVideoName()+".mp4");
        video.setStorePath(videoPath);
        log.info(videoPath);
            File dir = new File(dirPath);
            File fileToStore = new File(videoPath);
            if (!dir.isDirectory()) {
                if (dir.mkdirs()) {
                    if("储存成功".equals(StoreUtil.storeFile(uploadVideo, fileToStore))){
                        log.warn("已经储存成功了");
                        return CommonResult.builder()
                            .code(CommonResult.SUCCESS)
                            .detail("储存视频成功")
                            .result(video)
                            .build();
                    }
                    throw new RuntimeException("储存视频出现问题");
                }
                throw new RuntimeException("创建文件夹出现异常!");
            }
            if (fileToStore.isFile()) {
                throw new RuntimeException("文件已存在");
            }
            if("储存成功".equals(StoreUtil.storeFile(uploadVideo, fileToStore))){
                log.warn("已经储存成功了");
                return CommonResult.builder()
                    .code(CommonResult.SUCCESS)
                    .detail("储存视频成功")
                    .result(video)
                    .build();
            }
            throw new RuntimeException("储存视频出现问题");
    }

    @Override
    public CommonResult deleteVideo(Video video) {
        // TODO Auto-generated method stub
        if(StoreUtil.deleteFile(video.getStorePath()).equals("删除成功")){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除视频成功！").build();
        }
        throw new RuntimeException("删除视频文件发生异常！");
    }
}
