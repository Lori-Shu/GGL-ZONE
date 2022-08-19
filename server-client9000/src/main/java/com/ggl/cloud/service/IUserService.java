/*
*
*@Date:2022年5月06日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.User;

public interface IUserService extends IService<User>{
    CommonResult registry(User user);
    CommonResult deleteUser(User user);
    CommonResult updateUser(User user);
    User selectUser(User user);
    CommonResult getStatistics();
    CommonResult getUserDetail(User user);
    CommonResult uploadAvatar(User user);
    
}
