package com.ggl.cloud.controller;


import javax.annotation.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Friend;
import com.ggl.cloud.feignservice.ServerFeign;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/user/chat")
public class ChatController {
    @Resource
    ServerFeign serverFeign;
    private ObjectMapper objectMapper=new ObjectMapper();
    @PostMapping("/add")
    public CommonResult addFriend(@RequestBody Friend friend) {
       return serverFeign.addFriend(friend);
    }
    @PostMapping("/delete")
    public CommonResult deleteFriend(@RequestBody Friend friend) {
       return serverFeign.deleteFriend(friend);
    }

    @PostMapping("/select_friends")
    public CommonResult selectFriends(String userId)  {
        log.warn("userId"+userId);
        return serverFeign.selectFriends(userId);
    }

    @PostMapping("/select_stranger")
    public CommonResult selectStranger(String userId) {
        log.warn("查询的id"+userId);
        return serverFeign.selectStranger(userId);
    }

    
}
