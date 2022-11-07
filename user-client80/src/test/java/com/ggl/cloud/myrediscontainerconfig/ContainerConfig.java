package com.ggl.cloud.myrediscontainerconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.ggl.cloud.redislistener.MyRedisListener;

import lombok.extern.slf4j.Slf4j;

/**
 * description
 *
 * @author lori
 * createTime 2022年9月07日-17:17:02
 */
@Configuration
@Slf4j
public class ContainerConfig {
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory){
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        // ThreadPoolTaskExecutor myExecutor = new TaskExecutorBuilder().corePoolSize(2).maxPoolSize(5).keepAlive(Duration.ofMinutes(10)).build();
        // redisMessageListenerContainer.setTaskExecutor(myExecutor);
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        MyRedisListener myRedisListener = new MyRedisListener();
        MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter();
        messageListenerAdapter.setDelegate(myRedisListener);
        messageListenerAdapter.setDefaultListenerMethod("onMessage");
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, new PatternTopic("testTopic"));
        log.warn("已经注册监听器");
        return redisMessageListenerContainer;
    }
}
