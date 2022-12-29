package com.ggl.cloud.feignservice;


import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Friend;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Note;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.entity.Video;
/**
 * 
 * description
 *
 * @author Lori
 * createTime 2022年8月19日-下午3:51:10
 *
 */
@FeignClient(name = "ServerClient9000")
public interface ServerFeignClient {
    /**
     * register
     * @param user
     * @return
     */
    @PostMapping("server/user/registry")
    CommonResult registry(@RequestBody User user);
    /**
     * delete
     * @param user
     * @return
     */
    @PostMapping("server/user/delete")
    CommonResult delete(User user);
    /**
     * update
     * @param user
     * @return
     */
    @PostMapping("server/user/update")
    CommonResult update(User user);
    /**
     * add
     * @param note
     * @return
     */
    @PostMapping("server/note/add")
    CommonResult add(Note note);
    /**
     * delete
     * @param note
     * @return
     */
    @PostMapping("server/note/delete")
    CommonResult delete(Note note);
    /**
     * update
     * @param note
     * @return
     */
    @PostMapping("server/note/update")
    CommonResult update(Note note);
    /**
     * selectPage
     * @param notePageMap
     * @return
     */
    @PostMapping("server/note/selectPage")
    CommonResult selectPageNote(Map<String, Object> notePageMap);
    /**
     * upload
     * @param uploadMusic
     * @param music
     * @param suffix
     * @return
     */
    @PostMapping("server/music/upload")
    CommonResult uploadmusic(byte[] uploadMusic,@RequestParam("music") String music,
            @RequestParam("suffix") String suffix);
            /**
             * delete
             * @param music
             * @return
             */
    @PostMapping("server/music/delete")
    CommonResult delete(Music music);
    /**
     * selectPage
     * @param musicPageMap
     * @return
     */
    @PostMapping("server/music/selectPage")
    CommonResult selectPageMusic(Map<String, Object> musicPageMap);
    /**
     * upload
     * @param uploadVideo
     * @param video
     * @param suffix
     * @return
     */
    @PostMapping("server/video/upload")
    CommonResult uploadVideo(byte[] uploadVideo, @RequestParam("video") String video,
            @RequestParam("suffix") String suffix);
    /**
     * delete
     * @param video
     * @return
     */
    @PostMapping("server/video/delete")
    CommonResult delete(Video video);
    /**
     * selectPage
     * @param videoPageMap
     * @return
     */
    @PostMapping("server/video/selectPage")
    CommonResult selectPageVideo(Map<String, Object> videoPageMap);
    /**
     * add
     * @param friend
     * @return
     */
    @PostMapping("/server/chat/add")
    CommonResult addFriend(Friend friend);
    /**
     * delete
     * @param friend
     * @return
     */
    @PostMapping("/server/chat/delete")
    CommonResult deleteFriend(Friend friend);
    /**
     * select
     * @param userId
     * @return
     */
    @PostMapping("/server/chat/selectFriends")
    CommonResult selectFriends(@RequestParam("userId") String userId);
    /**
     * select
     * @param userId
     * @return
     */
    @PostMapping("/server/chat/selectStranger")
    CommonResult selectStranger(@RequestParam("userId") String userId);
    /**
     * detail
     * @param user
     * @return
     */
    @PostMapping("/server/user/getUserDetail")
    CommonResult getUserDetail(User user);
}
