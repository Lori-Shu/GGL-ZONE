package com.ggl.cloud.controller;


import javax.annotation.Resource;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Friend;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.service.IChatService;
import com.ggl.cloud.service.IUserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/server/chat")
public class ChatController {
    @Resource
    IChatService chatService;
    @Resource
    IUserService userService;
    @PostMapping("/add")
    public CommonResult addFriend(@RequestBody Friend friend) {
        log.warn("friend对象"+friend.toString());
        return chatService.addFriend(friend);
    }
    @PostMapping("/delete")
    public CommonResult deleteFriend(@RequestBody Friend friend) {
        log.warn("deleteTarget"+friend.getFriendId());
        return chatService.deleteFriend(friend);
    }

    @PostMapping("/selectFriends")
    public CommonResult selectFriends(String userId) {
        log.warn("userId"+userId);
        return chatService.selectFriends(userId);
    }
    @PostMapping("/selectStranger")
    public CommonResult selectStranger(String userId) {
        User stranger=new User();
        stranger.setUserId(userId);
        User user= userService.selectUser(stranger);
        log.warn(user.toString());
        if(!user.getId().equals("")){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("查询用户存在,是否添加为好友").build();
        }
        throw new RuntimeException("查询用户不存在");
    }

    
}
