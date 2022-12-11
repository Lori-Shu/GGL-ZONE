package com.ggl.cloud.service.impl;

import java.util.List;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Friend;
import com.ggl.cloud.mapper.ChatMapper;
import com.ggl.cloud.service.IChatService;

import jakarta.annotation.Resource;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:32:54
 *
 */
@Service
@Slf4j
public class ChatServiceImpl extends ServiceImpl<ChatMapper,Friend> implements IChatService {
    @Resource
    ChatMapper mapper;
    @Override
    @CachePut(value = "friendAddCache",key = "#friend.userId")
    public CommonResult addFriend(Friend friend) {
        if(saveOrUpdate(friend)){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("添加好友成功").build();
        }
        throw new RuntimeException("添加好友异常");
    }
    @Override
    @CacheEvict(value = "friendSelect",key = "#friend.userId")
    public CommonResult deleteFriend(Friend friend) {
        // 删除好友数据库
        if(removeById(friend)){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除好友成功").build();
        }
        throw new RuntimeException("删除好友异常");
    }

    @Override
    @Cacheable(value = "friendSelect",key = "#userId")
    public CommonResult selectFriends(String userId) {
        log.warn("userId"+userId);
        QueryWrapper<Friend> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Friend> result=list(queryWrapper);
        if(result!=null){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("查询好友成功").result(result).build();
        }
        throw new RuntimeException("查询好友异常");    
    }

}
