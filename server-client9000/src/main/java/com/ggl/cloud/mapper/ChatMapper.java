package com.ggl.cloud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ggl.cloud.entity.Friend;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:14:30
 *
 */
@Mapper
public interface ChatMapper extends BaseMapper<Friend> {
    /**
     * 查找好友名字列表
     * @param userId
     * @return
     */
    List<String> selectFriends(String userId);
    /**
     * 添加好友
     * @param userId
     * @param targetId
     * @return
     */
    Integer addFriend(@Param("userId") String userId, @Param("targetId") String targetId);
}
