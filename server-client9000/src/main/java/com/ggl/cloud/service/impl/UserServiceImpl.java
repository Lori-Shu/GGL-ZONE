package com.ggl.cloud.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.mapper.UserMapper;
import com.ggl.cloud.service.IUserService;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:35:54
 *
 */
@Service
@Slf4j
@Transactional(rollbackFor = RuntimeException.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements  IUserService,UserDetailsService {
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private UserMapper mapper;
    private final String DEFAULT_AUTH = "NORMAL";
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User tempUser = new User();
        tempUser.setUserId(s);
        User user=selectUser(tempUser);
        // log.info(user.toString());
        String empty = "";
        if (empty.equals(user.getId())&&null!=user.getId()) {
            throw new UsernameNotFoundException("用户不存在");
        }
        String auth = user.getAuth();
        log.warn(user.toString());
        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(
                user.getUserId(), user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(auth));
        return securityUser;
    }
        
    @Override
    // @SentinelResource(value = "registry",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
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
            user.setAuth(DEFAULT_AUTH);
            if (save(user)) {
                return CommonResult.builder().code(CommonResult.SUCCESS).detail("注册成功").build();
            }
            throw new RuntimeException("注册出现了一点问题");
        }
    }

    @Override
    // @SentinelResource(value = "deleteUser",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult deleteUser(User user) {
        // 用户删除方法
        if(removeById(user)){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("删除用户成功").build();
        }
        throw new RuntimeException("删除用户出现异常");
    }

    @Override
    // @SentinelResource(value = "updateUser",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
    public CommonResult updateUser(User user) {
        // 用户更新方法
        // !!!要乐观锁起效必须在所有需要锁的update方法先查询，将查到的version赋值后再调用修改
        User thisUser = getById(user.getId());
        String empty = "";
        if(! empty.equals(thisUser.getId())&&null!=thisUser.getId()){
            user.setVersion(thisUser.getVersion());
            if(updateById(user)){
                return CommonResult.builder().code(CommonResult.SUCCESS).detail("修改用户成功").build();
            }
            throw new RuntimeException("修改用户出现了问题");
        }
        throw new RuntimeException("不存在此用户");
    }

    @Override
    // @SentinelResource(value = "selectUser",blockHandler = "defaultBlock",blockHandlerClass = {BlockHandlerClass.class})
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
        Map<String,Integer> resultMap=new HashMap<>(1);
        resultMap.put("userRegistryCount", registryCount);
        return CommonResult.builder().code(CommonResult.SUCCESS).detail("统计成功").result(resultMap).build();
    }

    @Override
    public CommonResult getUserDetail(User user) {
        // 查询用户信息方法
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId());
        User one = getOne(queryWrapper);
        // 去掉敏感内容
        if (one != null) {
            one.setPassword("");
            one.setUpdateTime(null);
            log.warn("查询到的用户id:" + one.getId());
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("查询用户信息成功").result(one).build();
        }
        throw new RuntimeException("未查询到记录");
    }

    @Override
    public CommonResult uploadAvatar(User user) {
        //更新数据库里的头像src(根据用户id)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getUserId());
        User one = getOne(queryWrapper);
        one.setAvatar(user.getAvatar());
        boolean res = updateById(one);
        if(res){
            return CommonResult.builder().code(CommonResult.SUCCESS).detail("头像上传成功").build();
        }
        throw new RuntimeException("数据库修改出现异常");
    }
}
