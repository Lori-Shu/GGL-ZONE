package com.ggl.cloud.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.service.IMusicService;
import com.ggl.cloud.service.feignservice.ResourceFeign;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("server/music")
public class MusicController {
        @Resource
        private IMusicService service;
        @Resource
        private ResourceFeign feign;
        ObjectMapper om=new ObjectMapper();

    @PostMapping("upload")
    public CommonResult uploadmusic(@RequestBody byte[] uploadMusic,String music,String suffix) throws JsonMappingException, JsonProcessingException{
            CommonResult result=feign.uploadMusic(uploadMusic, music,suffix);
            if(result.getCode()==CommonResult.SUCCESS){
                    Music resolvedMusic = om.convertValue(result.getResult(), Music.class);
                log.warn(resolvedMusic.toString());
            return service.uploadMusic(resolvedMusic);
        }
        throw new RuntimeException("储存音乐发生异常");
    }

        @PostMapping("delete")
        public CommonResult delete(@RequestBody Music music) {
                log.warn(music.toString());
                CommonResult result=feign.deleteMusic(music);
                if(result.getCode()==CommonResult.SUCCESS){
                return service.deleteMusic(music);
        }
        throw new RuntimeException("删除音乐出现异常");
        }

        @PostMapping("selectPage")
        public CommonResult selectPageMusic(@RequestBody Map<String, Object> musicPageMap)throws JsonProcessingException {
                log.warn("map"+musicPageMap.toString());
                int pageNumber = (int) musicPageMap.get("pageNumber");
                int pageSize = (int) musicPageMap.get("pageSize");
                log.warn("pageSize="+pageSize);
                Object musicObj = musicPageMap.get("music");
                String musicJson = om.writeValueAsString(musicObj);
                log.warn(musicJson);
                Music music = om.readValue(musicJson, Music.class);
                return service.selectMusicPage(pageNumber, pageSize, music);
        }
        @PostMapping("getStatistics")
        public CommonResult getStatistics() {
                return service.getStatistics();
        }
        @PostMapping("plusDownload")
        public CommonResult plusDownload(@RequestBody Music music) {
                return service.plusDownload(music);
        }
}
