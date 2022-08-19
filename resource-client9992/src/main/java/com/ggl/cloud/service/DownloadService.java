package com.ggl.cloud.service;


import java.io.IOException;

import com.ggl.cloud.entity.Music;

/**
 *@Author Lori Shu
 *@Date 2022/4/23
 */
public interface DownloadService {
    /**
     *  description 通过字节的方式下载音乐文件
     * 
     * @param music
     * @return
     * @throws IOException
     */
    byte[] downloadMusic(Music music) throws IOException;

    // void downloadVideo(Video video);
    /**
     * description 获得本地文件的字节
     * @param path
     * @return
     * @throws IOException
     */
    byte[] getFileBytes(String path) throws IOException;
}
