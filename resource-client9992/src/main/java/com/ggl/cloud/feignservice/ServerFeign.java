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
    @PostMapping("server/music/plusDownload")
    CommonResult plusDownload(Music music);
    @PostMapping("server/user/uploadAvatar")
    CommonResult uploadAvatar(User user);
}
