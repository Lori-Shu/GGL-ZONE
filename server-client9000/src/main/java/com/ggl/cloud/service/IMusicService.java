/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;

public interface IMusicService extends IService<Music>{
    CommonResult uploadMusic(Music music);
    CommonResult deleteMusic(Music music);
    CommonResult selectMusicPage(int pageNumber,int pageSize,Music music);
    CommonResult getStatistics();
    
}
