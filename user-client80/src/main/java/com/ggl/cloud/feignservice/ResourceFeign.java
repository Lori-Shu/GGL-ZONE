/*
*
*@Date:2022年5月08日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.feignservice;

import java.io.File;
import java.io.InputStream;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ResourceClient9992")
public interface ResourceFeign {
    @PostMapping("resource/download/music")
    File download(Music music);
    @PostMapping("resource/download/video")
    File download(Video video);
    @PostMapping("resource/uploadAvatar")
    CommonResult uploadAvatar(byte[] avatar,@RequestParam("userId") String userId,
            @RequestParam("suffix") String suffix);
}
