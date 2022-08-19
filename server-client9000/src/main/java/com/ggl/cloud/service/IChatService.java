package com.ggl.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Friend;

/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:18:25
 *
 */
public interface IChatService extends IService<Friend> {
    /**
     * 添加好友
     * @param friend
     * @return
     */
    CommonResult addFriend(Friend friend);
    /**
     * 删除好友
     * @param friend
     * @return
     */
    CommonResult deleteFriend(Friend friend);
    /**
     * 查询好友
     * @param userId
     * @return
     */
    CommonResult selectFriends(String userId);

    
}
