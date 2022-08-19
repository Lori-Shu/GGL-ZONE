package com.ggl.cloud.service.impl;
/*
 *@Author Lori Shu
 *@Date 2022/4/23
 */

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.feignservice.ServerFeign;
import com.ggl.cloud.service.DownloadService;
import com.ggl.cloud.service.IResourceService;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:49:53
 *
 */
@Service
@Slf4j
@Transactional(rollbackFor = RuntimeException.class)
@EnableAsync
public class DownloadServiceImpl implements DownloadService {
    @Resource
    private IResourceService service;
    @Resource
    private ServerFeign serverFeign;

    @Resource
    private RocketMQTemplate rocketMqTemplate;

    @Override
    public byte[] downloadMusic(Music music) throws IOException {
        log.warn("进入download...service");
        QueryWrapper<com.ggl.cloud.entity.Resource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("resource_name", music.getMusicName());
        com.ggl.cloud.entity.Resource one = service.getOne(queryWrapper);
        if (one != null) {
            service.updateById(one);
        } else {
            com.ggl.cloud.entity.Resource resource = new com.ggl.cloud.entity.Resource();
            resource.setRequestType("download");
            resource.setResourceName(music.getMusicName());
            resource.setResourceType("music");
            service.save(resource);
        }
        serverFeign.plusDownload(music);
        // File file = new File(music.getStorePath());

        return getFileBytes(music.getStorePath());
        // throw new FileNotFoundException("文件未找到！！");
    }


    @Override
    public byte[] getFileBytes(String path) throws IOException {
        // 获得文件字节数组用于传输
        try (FileInputStream fis = new FileInputStream(path);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            byte[] bytes = new byte[1024];
            for (int readCount = 1; readCount > 0;) {
                if ((readCount = fis.read(bytes)) > 0) {
                    baos.write(bytes, 0, readCount);
                }
            }
            return baos.toByteArray();
        } catch (Exception e) {
            // 处理io异常
            throw e;
        }
    }
}
