/*
*
*@Date:2022年5月17日
*
*@Author:Lori Shu
*
*/
package com.ggl.cloud.service.feignservice;

import com.ggl.cloud.entity.CommonResult;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("ServerClient9000")
public interface ServerFeign {
    @PostMapping("server/music/getStatistics")
    public CommonResult getMusicStatistics();
    @PostMapping("server/user/getStatistics")
    public CommonResult getUserStatistics();
}
