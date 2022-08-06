package com.ggl.cloud.service;

import java.io.FileNotFoundException;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Resource;
import com.ggl.cloud.entity.Video;

/**
 * <p>
 * 资源表 服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-07-05
 */
public interface IResourceService extends IService<Resource> {
    String getHalfSrcFromRequest();

    String getDirPathFromMyuri(String src);

    CommonResult uploadMusic(byte[] uploadMusic, Music music, String suffix) throws FileNotFoundException ;

    CommonResult deleteMusic(Music music);

    CommonResult uploadVideo(byte[] uploadVideo, Video video, String suffix) throws FileNotFoundException ;

    CommonResult deleteVideo(Video video);

    String uploadAvatar(byte[] avatar, String userId,String suffix);

}
