package com.ggl.cloud.config;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
// mybatis-plus自动注入配置类
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午2:16:33
 *
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        System.out.println("进入自动填充增加方法");
        // 起始版本 3.3.0(推荐使用)
        // this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); 
        // 或者
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        // 起始版本 3.3.3(推荐)
        this.strictInsertFill(metaObject, "updateTime", ()-> LocalDateTime.now(), LocalDateTime.class); 
        // 或者
        // this.fillStrategy(metaObject, "createTime", LocalDateTime.now()); // 也可以使用(3.3.0 该方法有bug)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        System.out.println("进入自动填充修改方法了诶");
        // 起始版本 3.3.0(推荐)
        // this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); 
        // 或者
        // 起始版本 3.3.3(推荐)
        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class); 
        // 或者
        // 也可以使用(3.3.0 该方法有bug)
        // this.fillStrategy(metaObject, "updateTime", LocalDateTime.now()); 
    }
}