package com.ggl.cloud.feignservice;


import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ggl.cloud.entity.CommonResult;
import com.ggl.cloud.entity.Friend;
import com.ggl.cloud.entity.Music;
import com.ggl.cloud.entity.Note;
import com.ggl.cloud.entity.User;
import com.ggl.cloud.entity.Video;

@FeignClient(value = "ServerClient9000")
public interface ServerFeign {
    @PostMapping("server/user/registry")
    CommonResult registry(@RequestBody User user);
    @PostMapping("server/user/delete")
    CommonResult delete(User user);

    @PostMapping("server/user/update")
    CommonResult update(User user);
    @PostMapping("server/note/add")
    CommonResult add(Note note);
    @PostMapping("server/note/delete")
    CommonResult delete(Note note);

    @PostMapping("server/note/update")
    CommonResult update(Note note);
    @PostMapping("server/note/selectPage")
    CommonResult selectPageNote(Map<String,Object> notePageMap);
    @PostMapping("server/music/upload")
    CommonResult uploadmusic(byte[] uploadMusic,@RequestParam("music") String music,
            @RequestParam("suffix") String suffix);
    @PostMapping("server/music/delete")
    CommonResult delete(Music music);

    @PostMapping("server/music/selectPage")
    CommonResult selectPageMusic(Map<String,Object> musicPageMap);
    @PostMapping("server/video/upload")
    CommonResult uploadVideo(byte[] uploadVideo,@RequestParam("video") String video,@RequestParam("suffix")String suffix);
    @PostMapping("server/video/delete")
    CommonResult delete(Video video);

    @PostMapping("server/video/selectPage")
    CommonResult selectPageVideo(Map<String,Object> videoPageMap) ;
    @PostMapping("/server/chat/add")
    CommonResult addFriend(Friend friend);
    @PostMapping("/server/chat/delete")
    CommonResult deleteFriend(Friend friend);

    @PostMapping("/server/chat/selectFriends")
    CommonResult selectFriends(@RequestParam("userId") String userId);
    @PostMapping("/server/chat/selectStranger")
    CommonResult selectStranger(@RequestParam("userId") String userId);
    @PostMapping("/server/user/getUserDetail")
    CommonResult getUserDetail(User user);
}
