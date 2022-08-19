/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service.impl;


import java.io.File;
import java.io.FileNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Resource;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.mapper.ResourceMapper;
import com.ggl.cloud.service.IResourceService;
import com.ggl.cloud.utils.StoreUtil;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@Transactional
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper,Resource> implements IResourceService {
    @Override
    public String getHalfSrcFromRequest() {
        //通过HttpServletRequest获得请求地址前半部分 
        // StringBuffer url=request.getRequestURL();
        // int index=url.indexOf("resource");
        // String halfSrc=url.substring(0, index);
        return "http://localhost:9992/";
    }
    @Override
    public String getDirPathFromMyuri(String myUri) {
// 通过传入的uri获得存储的具体path
        return "E://GGL-ZONE-resources/"+myUri;
    }

    @Override 
    public CommonResult uploadMusic(byte[] uploadMusic,Music music,String suffix) throws  FileNotFoundException  {
        // 上传音乐方法
        String halfSrc=getHalfSrcFromRequest();
        StringBuffer myUri=new StringBuffer();
        myUri.append("music");
        myUri.append("/"+music.getMusician()+"/");
        String dirPath=getDirPathFromMyuri(myUri.toString());
        String musicPath=dirPath+music.getMusicName()+suffix;
        music.setSrc(halfSrc+myUri.toString()+music.getMusicName()+suffix);
        music.setStorePath(musicPath);
        //        log.info(realPath);
        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
        }
        File fileToStore = new File(musicPath);
        String storeFileRes = StoreUtil.storeFile(uploadMusic, fileToStore);
        if(storeFileRes.equals("储存成功")){
            return CommonResult.builder().code(CommonResult.SUCCESS).result(music).build();
        }

            throw new RuntimeException("储存音乐出现问题");
    }

    @Override
    public CommonResult deleteMusic(Music music) {
        // 删除音乐方法
        if(StoreUtil.deleteFile(music.getStorePath()).equals("删除成功")){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除音乐成功！").build();
        }
        throw new RuntimeException("删除音乐文件发生异常！");
    }

    @Override
    public CommonResult uploadVideo(byte[] uploadVideo,Video video, String suffix) throws FileNotFoundException  {
        //上传视频方法
        String halfSrc=getHalfSrcFromRequest();
        StringBuffer myUri=new StringBuffer();
        myUri.append("video");
        myUri.append("/"+video.getVideoAuthor()+"/");
        String dirPath=getDirPathFromMyuri(myUri.toString());
        String videoPath=dirPath+video.getVideoName()+suffix;
        video.setSrc(halfSrc+myUri.toString()+video.getVideoName()+suffix);
        video.setStorePath(videoPath);
        log.info(videoPath);
        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            dir.mkdirs();
            }
            File fileToStore = new File(videoPath);
            String res = StoreUtil.storeFile(uploadVideo, fileToStore);
            if (res.equals("储存成功")) {
                return CommonResult.builder().code(CommonResult.SUCCESS).result(video).build();
            }
            throw new RuntimeException("储存视频出现问题");
    }

    @Override
    public CommonResult deleteVideo(Video video) {
        // 删除视频方法
        if(StoreUtil.deleteFile(video.getStorePath()).equals("删除成功")){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除视频成功！").build();
        }
        throw new RuntimeException("删除视频文件发生异常！");
    }
    @Override
    public String uploadAvatar(byte[] avatar, String userId,String suffix) {
        // 储存Avatar方法
        String halfSrc = getHalfSrcFromRequest();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("avatar/");
        stringBuilder.append(userId);
        stringBuilder.append(suffix);
        String string = stringBuilder.toString();
        String dirPathFromMyuri = getDirPathFromMyuri(string);
        File file = new File(dirPathFromMyuri);
        String storeFile = StoreUtil.storeFile(avatar, file);
        log.warn(string);
        log.warn(dirPathFromMyuri);
        if (storeFile.equals("储存成功")) {
            return halfSrc + string;
        }
        return null;
    }
}
