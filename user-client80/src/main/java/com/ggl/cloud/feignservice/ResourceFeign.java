/*
*
*@Date:2022年5月08日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;

@FeignClient("ResourceClient9992")
public interface ResourceFeign {
    @PostMapping("resource/download/music")
    byte[] download(Music music);
    // @PostMapping("resource/download/video")
    // Long download(Video video);
    @PostMapping("resource/uploadAvatar")
    CommonResult uploadAvatar(byte[] avatar,@RequestParam("userId") String userId,
            @RequestParam("suffix") String suffix);
}
