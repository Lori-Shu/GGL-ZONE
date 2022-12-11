package com.ggl.cloud.controller;



import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.service.IUserService;

import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:11:29
 *
 */
@RestController
@RequestMapping("server/user")
@Slf4j
public class UserController {
    @Resource
    private IUserService service;
    @PostMapping("registry")
    public CommonResult registry(@RequestBody User user) {
        log.warn(user.toString());
        return service.registry(user);
    }
    @PostMapping("delete")
    public CommonResult delete(@RequestBody User user) {
        return service.deleteUser(user);
    }

    @PostMapping("update")
    public CommonResult update(@RequestBody User user) {
        return service.updateUser(user);
    }

    @PostMapping("getStatistics")
    public CommonResult getStatistics() {
        return service.getStatistics();
    }
    @PostMapping("getUserDetail")
    public CommonResult getUserDetail(@RequestBody User user) {
        return service.getUserDetail(user);
    }
    @PostMapping("uploadAvatar")
    public CommonResult uploadAvatar(@RequestBody User user) {
        return service.uploadAvatar(user);
        
    }
}
