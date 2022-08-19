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
    /**
     * insert
     * @param note
     * @return
     */
    CommonResult insertNote(Note note);
    /**
     * delete
     * @param note
     * @return
     */
    CommonResult deleteNote(Note note);
    /**
     * update
     * @param note
     * @return
     */
    CommonResult updateNote(Note note);
    /**
     * selectPage
     * @param pageNumber
     * @param pageSize
     * @param note
     * @return
     */
    CommonResult selectPage(int pageNumber,int pageSize,Note note);

}
