/*
*
*@Date:2022年5月08日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.controller;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Note;
import com.ggl.cloud.feignservice.ServerFeign;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("user/note")
@Slf4j
public class NoteController {
    @Resource
    private ServerFeign service;
    @GetMapping("add")
    public CommonResult addNote(@RequestBody Note note) {
        return service.add(note);
    }
    @GetMapping("delete")
    public CommonResult deleteNote(@RequestBody Note note) {
        return service.delete(note);
    }
    @GetMapping("update")
    public CommonResult updateNote(@RequestBody Note note) {
//        log.info(note.toString());
        return service.update(note);
//        System.out.println(result);
//        log.info(result.toString());

    }
    @PostMapping("select_page")
    public CommonResult selectPage(int pageNumber,int pageSize,@RequestBody Note note) {
        ConcurrentHashMap<String,Object> map=new ConcurrentHashMap<>();
        map.put("pageNumber", pageNumber);
        map.put("pageSize", pageSize);
        log.warn(note.toString());
        map.put("note", note);
        return service.selectPageNote(map);
    }
}