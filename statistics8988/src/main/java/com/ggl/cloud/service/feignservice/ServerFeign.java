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
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:45:33
 *
 */
@FeignClient("ServerClient9000")
public interface ServerFeign {
    /**
     * music
     * @return
     */
    @PostMapping("server/music/getStatistics")
    public CommonResult getMusicStatistics();
    /**
     * user
     * @return
     */
    @PostMapping("server/user/getStatistics")
    public CommonResult getUserStatistics();
}
