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
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:50:44
 *
 */
@FeignClient("ResourceClient9992")
public interface ResourceFeign {
    /**
     * download
     * @param music
     * @return
     */
    @PostMapping("resource/download/music")
    byte[] download(Music music);
    // @PostMapping("resource/download/video")
    // Long download(Video video);
    /**
     * avatar
     * @param avatar
     * @param userId
     * @param suffix
     * @return
     */
    @PostMapping("resource/uploadAvatar")
    CommonResult uploadAvatar(byte[] avatar,@RequestParam("userId") String userId,
            @RequestParam("suffix") String suffix);
}
