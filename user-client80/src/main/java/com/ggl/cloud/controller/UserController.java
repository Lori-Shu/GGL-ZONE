package com.ggl.cloud.controller;



import java.io.IOException;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.feignservice.ResourceFeign;
import com.ggl.cloud.feignservice.ServerFeign;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:49:58
 *
 */
@RestController
@Slf4j
@RequestMapping("user")
public class UserController {
    @Resource
    private ServerFeign service;

    @Resource
    private ResourceFeign resourceFeign;

    @Resource
    private ObjectMapper objectMapper;
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
    public CommonResult updateUser(@RequestBody User user) {
        return service.update(user);
    }
    @PostMapping("getUserDetail")
    public CommonResult getUserDetail(@RequestBody User user) {
        return service.getUserDetail(user);
    }
    @PostMapping("uploadAvatar")
    public CommonResult uploadAvatar(MultipartFile avatar, @RequestParam("user") String user) throws IOException  {
        User readUser = objectMapper.readValue(user, User.class);
        log.warn(avatar.getOriginalFilename());
        String originalFilename = avatar.getOriginalFilename();
        int indexOf = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(indexOf);
        return resourceFeign.uploadAvatar(avatar.getBytes(), readUser.getUserId(),suffix);
    }
}
