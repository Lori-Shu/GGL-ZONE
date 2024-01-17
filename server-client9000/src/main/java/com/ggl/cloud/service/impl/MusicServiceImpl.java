/*
 * @Author: Lori Shu
 * @Date: 2022-05-07 14:56:57
 * @LastEditors: Lori Shu
 * @LastEditTime: 2022-07-06 13:17:12
 */
/*
*
*@Date:2022年5月07日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;



import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.mapper.MusicMapper;
import com.ggl.cloud.service.IMusicService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:33:00
 *
 */
@Service
@Slf4j
@Transactional(rollbackFor = RuntimeException.class)
public class MusicServiceImpl extends ServiceImpl<MusicMapper, Music> implements IMusicService {
    @Resource
    MusicMapper mapper;

    @Override
    @CacheEvict(value = "musicSelectPage", allEntries = true)
    // @SentinelResource(value = "uploadMusic", blockHandler = "defaultBlock", blockHandlerClass = {
            // BlockHandlerClass.class })
    public CommonResult uploadMusic(Music music) {
        if (save(music)) {
            
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("上传音乐成功").build();
        }

        throw new RuntimeException("保存音乐记录出现了问题！");
    }

    @Override
    @CacheEvict(value = "musicSelectPage", allEntries = true)
    // @SentinelResource(value = "deleteMusic", blockHandler = "defaultBlock", blockHandlerClass = {
            // BlockHandlerClass.class })
    public CommonResult deleteMusic(Music music) {
        if (removeById(music)) {
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除音乐成功").build();
        }
        throw new RuntimeException("删除音乐记录出现问题");
    }

    @Override
    @Cacheable(value = "musicSelectPage", key = "T(String).valueOf(#pageNumber).concat('-').concat(#pageSize).concat('-').concat(#music.toString())")
    public CommonResult selectMusicPage(int pageNumber, int pageSize, Music music) {
        Page<Music> musicPage = new Page<>(pageNumber, pageSize);
        QueryWrapper<Music> queryWrapper = new QueryWrapper<>();
        if (StringUtils.hasLength(music.getUserId())) {
            throw new RuntimeException("userId不能为空");
        }
        if (!StringUtils.hasLength(music.getMusicName())) {
            queryWrapper.like("music_name", music.getMusicName());
        }
        if (!StringUtils.hasLength(music.getUserId())) {
            queryWrapper.like("user_id", music.getUserId());
        }

        if (!StringUtils.hasLength(music.getMusician())) {
            queryWrapper.like("musician", music.getMusician());
        }
        if (!StringUtils.hasLength(music.getAlbum())) {
            queryWrapper.like("album", music.getAlbum());
        }
        queryWrapper.orderByDesc("update_time");
        long count = count(queryWrapper);
        page(musicPage, queryWrapper);
        if (musicPage.getRecords().size() > 0) {
            Map<String, Object> resMap = new HashMap<>(2);
            resMap.put("total", count);
            resMap.put("list", musicPage.getRecords());
            log.warn(musicPage.getRecords().toString());
            return CommonResult.builder()
                    .code(CommonResult.SUCCESS)
                    .detail("查询音乐页面成功")
                    .result(resMap)
                    .build();
        }
        throw new RuntimeException("查询音乐页面出现问题，结果为空！");
    }

    @Override
    public CommonResult getStatistics() {
        // 提供给数据服务的数据查询接口
        LocalDateTime nowTime = LocalDateTime.now().plusDays(-1);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String s = dateTimeFormatter.format(nowTime);
        int uploadCount = mapper.getUploadCount(s);
        log.warn("uploadCount" + uploadCount);
        int deleteCount = mapper.getDeleteCount(s);
        log.warn("deleteCount" + deleteCount);
        Map<String, Integer> resultMap = new HashMap<>(2);
        resultMap.put("musicUploadCount", uploadCount);
        resultMap.put("musicDeleteCount", deleteCount);
        return CommonResult.builder().code(CommonResult.SUCCESS).detail("统计成功").result(resultMap).build();
    }

    @Override
    public CommonResult plusDownload(Music music) {
        // 增加统计音乐资源下载次数
        Music mc = getById(music.getId());
        mc.setDownloadTimes(mc.getDownloadTimes() + 1);
        updateById(mc);
        return CommonResult.builder().code(CommonResult.SUCCESS).build();
    }

}
