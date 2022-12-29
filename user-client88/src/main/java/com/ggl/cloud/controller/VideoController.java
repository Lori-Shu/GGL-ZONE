package com.ggl.cloud.controller;


import java.io.IOException;
import java.util.HashMap;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Video;
import com.ggl.cloud.feignservice.ServerFeignClient;

import jakarta.annotation.Resource;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:50:05
 *
 */
@RestController
@RequestMapping("user/video")
public class VideoController {
    @Resource
    private ServerFeignClient service;

    @PostMapping("upload")
    public CommonResult upload(MultipartFile uploadVideo,@RequestParam("video")String video) throws IOException{
        // File file=File.createTempFile(uploadVideo.getOriginalFilename(), ".mp4");
        String originalFilename = uploadVideo.getOriginalFilename();
        int lastIndex = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(lastIndex);
        return service.uploadVideo(uploadVideo.getBytes(), video,substring);
    }
    @GetMapping("delete")
    public CommonResult deleteVideo(@RequestBody Video video) {
        return service.delete(video);
    }

    @PostMapping("select_page")
    public CommonResult selectPage(int pageNumber,int pageSize,@RequestBody Video video) {
        HashMap<String,Object> map=new HashMap<>(3);
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        map.put("video", video);
        return service.selectPageVideo(map);

    }

    
}
