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
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:23:59
 *
 */
public interface IUserService extends IService<User> {
    /**
     * register
     * @param user
     * @return
     */
    CommonResult registry(User user);
    /**
     * delete
     * @param user
     * @return
     */
    CommonResult deleteUser(User user);
    /**
     * update
     * @param user
     * @return
     */
    CommonResult updateUser(User user);
    /**
     * select
     * @param user
     * @return
     */
    User selectUser(User user);
    /**
     * statistic
     * @return
     */
    CommonResult getStatistics();
    /**
     * detail
     * @param user
     * @return
     */
    CommonResult getUserDetail(User user);
    /**
     * avatar
     * @param user
     * @return
     */
    CommonResult uploadAvatar(User user);
    
}
