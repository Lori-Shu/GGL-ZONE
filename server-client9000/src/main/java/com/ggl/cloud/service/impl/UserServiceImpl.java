package com.ggl.cloud.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ggl.cloud.config.BlockHandlerClass;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.mapper.UserMapper;
import com.ggl.cloud.service.IUserService;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements  IUserService,UserDetailsService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserMapper mapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User tempUser = new User();
        tempUser.setUserId(s);
        User user=selectUser(tempUser);
        // log.info(user.toString());
        if (user.getId().equals("")) {
            throw new UsernameNotFoundException("用户不存在");
        }
        String auth = user.getAuth();
        log.warn(user.toString());
        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(
                user.getUserId(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(auth));
        return securityUser;
    }
        
    @Override
    @SentinelResource(value = "registry",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult registry(User user) {
        // 用户注册方法
        // log.info(userId+"###"+password);
        log.warn(user.toString());
        String userId=user.getUserId();
        String password=user.getPassword();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        User userOne = getOne(queryWrapper);
        if (userOne != null) {
            return CommonResult.builder().code(CommonResult.ERROR).detail("用户已存在").build();
        } else {
            user.setPassword(passwordEncoder.encode(password));
            if (save(user)) {
                return CommonResult.builder().code(CommonResult.SUCCESS).detail("注册成功").build();
            }
            throw new RuntimeException("注册出现了一点问题");
        }
    }

    @Override
    @SentinelResource(value = "deleteUser",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult deleteUser(User user) {
        // 用户删除方法
        if(removeById(user)){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除用户成功").build();
        }
        throw new RuntimeException("删除用户出现异常");
    }

    @Override
    @SentinelResource(value = "updateUser",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult updateUser(User user) {
        // 用户更新方法
        // !!!要乐观锁起效必须在所有需要锁的update方法先查询，将查到的version赋值后再调用修改
        User thisUser=getById(user.getId());
        if(!thisUser.getId().equals("")){
            user.setVersion(thisUser.getVersion());
            if(updateById(user)){
                return CommonResult.builder().code(CommonResult.SUCCESS).detail("修改用户成功").build();
            }
            throw new RuntimeException("修改用户出现了问题");
        }
        throw new RuntimeException("不存在此用户");
    }

    @Override
    @SentinelResource(value = "selectUser",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public User selectUser(User user) {
        // 查询单个用户
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        log.warn("userId"+user.getUserId());
        queryWrapper.eq("user_id", user.getUserId());
        return getOne(queryWrapper);
    }

    @Override
    public CommonResult getStatistics() {
        // 提供给数据服务的接口
        LocalDateTime nowTime=LocalDateTime.now().plusDays(-1);
        DateTimeFormatter dateTimeFormatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String s=dateTimeFormatter.format(nowTime);
        int registryCount=mapper.getRegistryCount(s);
        log.warn("registryCount"+registryCount);
        // int deleteCount=mapper.getDeleteCount(s);
        // log.warn("deleteCount"+deleteCount);
        Map<String,Integer> resultMap=new ConcurrentHashMap<>();
        resultMap.put("userRegistryCount", registryCount);
        return CommonResult.builder().code(CommonResult.SUCCESS).detail("统计成功").result(resultMap).build();
    }
}
