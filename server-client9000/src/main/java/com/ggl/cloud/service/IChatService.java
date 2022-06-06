package com.ggl.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Friend;


public interface IChatService extends IService<Friend> {
    CommonResult addFriend(Friend friend);
    CommonResult deleteFriend(Friend friend);
    CommonResult selectFriends(String userId);

    
}
