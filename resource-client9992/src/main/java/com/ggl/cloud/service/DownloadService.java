package com.ggl.cloud.service;


import java.io.IOException;

import com.ggl.cloud.entity.Music;

/*
 *@Author Lori Shu
 *@Date 2022/4/23
 */
public interface DownloadService {
    byte[] downloadMusic(Music music) throws IOException;

    // void downloadVideo(Video video);
    
    byte[] getFileBytes(String path) throws IOException;
}
