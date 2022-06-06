/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;

public interface ResourceService {
    String getHalfSrcFromRequest(HttpServletRequest request);
    String getDirPathFromMyuri(String src);
    CommonResult uploadMusic(File uploadMusic,Music music,HttpServletRequest request) throws JsonProcessingException;
    CommonResult deleteMusic(Music music);
    CommonResult uploadVideo(File uploadVideo,Video video,HttpServletRequest request)throws JsonProcessingException;
    CommonResult deleteVideo(Video video);

}
