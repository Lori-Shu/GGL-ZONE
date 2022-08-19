/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service.feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:32:05
 *
 */
@FeignClient("ResourceClient9992")
public interface ResourceFeign {
        /**
         * upload
         * @param uploadMusic
         * @param music
         * @param suffix
         * @return
         */
    @PostMapping(value = "resource/uploadMusic")
    CommonResult uploadMusic(byte[] uploadMusic,@RequestParam("music") String music,
                    @RequestParam("suffix") String suffix);
            /**
             * delete
             * @param music
             * @return
             */
    @PostMapping("resource/deleteMusic")
    CommonResult deleteMusic(Music music);
    /**
     * upload
     * @param uploadVideo
     * @param video
     * @param suffix
     * @return
     */
    @PostMapping(value = "resource/uploadVideo")
    CommonResult uploadVideo(byte[] uploadVideo,@RequestParam("video") String video,
                    @RequestParam("suffix") String suffix);
            /**
             * delete
             * @param video
             * @return
             */
    @PostMapping("resource/deleteVideo")
    CommonResult deleteVideo(Video video);

    
}
