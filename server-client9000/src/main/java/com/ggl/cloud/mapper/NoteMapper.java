package com.ggl.cloud.mapper;

import com.ggl.cloud.entity.Note;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author baomidou
 * @since 2022-05-01
 */
@Mapper
public interface NoteMapper extends BaseMapper<Note> {

}
