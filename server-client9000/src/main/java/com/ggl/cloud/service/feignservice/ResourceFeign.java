/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service.feignservice;

import java.io.File;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ResourceClient9992")
public interface ResourceFeign {
    @PostMapping(value = "resource/uploadMusic")
    CommonResult uploadMusic(byte[] uploadMusic,@RequestParam("music") String music,
            @RequestParam("suffix") String suffix);
    @PostMapping("resource/deleteMusic")
    CommonResult deleteMusic(Music music);
    @PostMapping(value = "resource/uploadVideo")
    CommonResult uploadVideo(byte[] uploadVideo,@RequestParam("video") String video,
            @RequestParam("suffix") String suffix);
    @PostMapping("resource/deleteVideo")
    CommonResult deleteVideo(Video video);

    
}
