package com.ggl.cloud.controller;



import javax.annotation.Resource;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.feignservice.ServerFeign;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("user")
public class UserController {
    @Resource
    private ServerFeign service;
    @PostMapping("registry")
    public CommonResult registry(@RequestBody User user) {
        log.warn(user.toString());
        // log.warn(request.getParameterNames().nextElement());
        return service.registry(user);
    }
    @PostMapping("delete_user")
    public CommonResult deleteUser(@RequestBody User user){
        return service.delete(user);

    }
    @PostMapping("update_user")
    public CommonResult updateUser(@RequestBody User user){
        return service.update(user);
    }
}
