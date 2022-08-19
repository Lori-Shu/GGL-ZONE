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
    /**
     * description 从请求url获得关心的uri
     * @return
     */
    String getHalfSrcFromRequest();
    /**
     * description 从uri获得文件夹path
     * @param src
     * @return
     */
    String getDirPathFromMyuri(String src);
    /**
     * description 保存音乐并写入数据库
     * @param uploadMusic
     * @param music
     * @param suffix
     * @return
     * @throws FileNotFoundException
     */

    CommonResult uploadMusic(byte[] uploadMusic, Music music, String suffix) throws FileNotFoundException;
    /**
     * 删除音乐
     * @param music
     * @return
     */

    CommonResult deleteMusic(Music music);
    /**
     * 上传视频并写入数据库
     * @param uploadVideo
     * @param video
     * @param suffix
     * @return
     * @throws FileNotFoundException
     */

    CommonResult uploadVideo(byte[] uploadVideo, Video video, String suffix) throws FileNotFoundException ;
    /**
     * 删除视频
     * @param video
     * @return
     */
    CommonResult deleteVideo(Video video);
    /**
     * 上传头像
     * @param avatar
     * @param userId
     * @param suffix
     * @return
     */
    String uploadAvatar(byte[] avatar, String userId,String suffix);

}
