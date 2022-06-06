package com.ggl.cloud.service;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Note;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baomidou
 * @since 2022-05-01
 */
public interface INoteService extends IService<Note> {
    CommonResult insertNote(Note note);
    CommonResult deleteNote(Note note);
    CommonResult updateNote(Note note);
    CommonResult selectPage(int pageNumber,int pageSize,Note note);

}
