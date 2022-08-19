/**
*
*@Date:2022年7月05日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.feignservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.User;

@FeignClient("ServerClient9000")
public interface ServerFeign {
    /**
     * description 增加数据库里访问次数的数据
     * 
     * @param music
     * @return 增加访问次数的结果
     */
    @PostMapping("server/music/plusDownload")
    CommonResult plusDownload(Music music);
    
    /**
     * description 更新数据库里头像的数据
     * 
     * @param user
     * @return 更新头像的结果
     */
    @PostMapping("server/user/uploadAvatar")
    CommonResult uploadAvatar(User user);
}
