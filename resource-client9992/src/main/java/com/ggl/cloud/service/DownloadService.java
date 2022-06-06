package com.ggl.cloud.service;


import java.io.File;

import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Video;

/*
 *@Author Lori Shu
 *@Date 2022/4/23
 */
public interface DownloadService {
    File downloadMusic(Music music);
    File downloadVideo(Video video);
}
