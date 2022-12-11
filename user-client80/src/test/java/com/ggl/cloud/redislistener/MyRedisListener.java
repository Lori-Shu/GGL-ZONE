package com.ggl.cloud.redislistener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.lang.Nullable;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author lori
 * createTime 2022年9月07日-15:36:23
 */
@Slf4j
public class MyRedisListener implements MessageListener{

    @Override
    public void onMessage(Message message, @Nullable byte[] pattern) {
        // 接收订阅
        log.warn("--接收到消息--{}",message.getBody().toString());  
        
    }
   
    
}
