package com.ggl.cloud.controller;

import java.util.Map;

import javax.annotation.Resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Note;
import com.ggl.cloud.service.INoteService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-04-30
 */
@RestController
@RequestMapping("server/note")
@Slf4j
public class NoteController {
    @Resource
    private INoteService service;

    @PostMapping("add")
    public CommonResult add(@RequestBody Note note){
        return service.insertNote(note);
    }
    @PostMapping("delete")
    public CommonResult delete(@RequestBody Note note){
        return service.deleteNote(note);

    }

    @PostMapping("update")
    public CommonResult update(@RequestBody Note note){
       return service.updateNote(note);
    }
    @PostMapping("selectPage")
    public CommonResult selectPageNote(@RequestBody Map<String,Object> notePageMap) throws JsonMappingException, JsonProcessingException {
        log.warn("map"+notePageMap);
        int pageNumber=(int) notePageMap.get("pageNumber");
        int pageSize=(int) notePageMap.get("pageSize");
        Object noteObj= notePageMap.get("note");
        ObjectMapper om=new ObjectMapper();
        String noteJson=om.writeValueAsString(noteObj);
        log.warn(noteJson);
        Note note=om.readValue(noteJson, Note.class);
        return service.selectPage(pageNumber, pageSize, note);
    }
}
