package com.ggl.cloud.service.impl;

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
import com.ggl.cloud.entity.Note;
import com.ggl.cloud.mapper.NoteMapper;
import com.ggl.cloud.service.INoteService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-01
 */
@Service
@Slf4j
@Transactional(rollbackFor = RuntimeException.class)
public class NoteServiceImpl extends ServiceImpl<NoteMapper, Note> implements INoteService {
    @Override
    @CacheEvict(value = "selectPageNote",allEntries = true)
    // @SentinelResource(value = "insertNote",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult insertNote(Note note){
        if(save(note)){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("插入成功").build();
        }
        return CommonResult.builder().code(CommonResult.ERROR).detail("插入失败").build();
    }

    @Override
    @CacheEvict(value = "selectPageNote",allEntries = true)
    // @SentinelResource(value = "deleteNote",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult deleteNote(Note note) {
        // 删除笔记接口
        log.warn(note.toString());
        if(removeById(note)){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除笔记成功").build();
        }
        throw new RuntimeException("删除笔记失败");
    }

    @Override
    @CacheEvict(value = "selectPageNote",allEntries = true)
    // @SentinelResource(value = "updateNote",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult updateNote(Note note) {
        // 更新笔记接口
             // 先查再改乐观锁才起效,version必须为查出来的值，否则会修改失败
            Note thisNote=getById(note.getId());
            note.setVersion(thisNote.getVersion());
            if(updateById(note)){
                return CommonResult.builder().code(CommonResult.SUCCESS).detail("修改成功").build();
            }
        throw new RuntimeException("修改出现异常");
    }

    @Override
    @Cacheable(value = "selectPageNote",key = "T(String).valueOf(#pageNumber).concat('-').concat(#pageSize).concat('-').concat(#note.toString())")
    public CommonResult selectPage(int pageNumber, int pageSize, Note note) {
        // 分页查询笔记
        if(StringUtils.hasLength(note.getUserId())){
            throw new RuntimeException("userId不能为空");
        }
        Page<Note> page=new Page<>(pageNumber,pageSize);
        QueryWrapper<Note> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.hasLength(note.getTitle())){
            queryWrapper.like("title", note.getTitle());
        }
        if(!StringUtils.hasLength(note.getUserId())){
            queryWrapper.like("user_id", note.getUserId());
        }

        if(!StringUtils.hasLength(note.getYear())){
            queryWrapper.like("year", note.getYear());
        }
        if(!StringUtils.hasLength(note.getMonth())){
            queryWrapper.like("month", note.getMonth());
        }
        if(!StringUtils.hasLength(note.getDay())){
            queryWrapper.like("day", note.getDay());
        }
        if(!StringUtils.hasLength(note.getContent())){
            queryWrapper.like("content", note.getContent());
        }
        queryWrapper.orderByDesc("update_time");
        long count = count(queryWrapper);
        page(page,queryWrapper); 
        log.warn(note.toString());
        if(page.getRecords().size()>0){
            log.warn(page.getRecords().toString());
            Map<String,Object> resultMap=new HashMap<>(2);
            resultMap.put("total", count);
            resultMap.put("list", page.getRecords());
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("查询成功").result(resultMap).build();
        }
        throw new RuntimeException("查询过程出现问题！");
    } 

}
