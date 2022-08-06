package com.ggl.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggl.cloud.entity.Friend;

@Mapper
public interface ChatMapper extends BaseMapper<Friend> {
    List<String> selectFriends(String userId);

    Integer addFriend(@Param("userId") String userId, @Param("targetId") String targetId);
}
