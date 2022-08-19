// package com.ggl.cloud.mapper;

// import com.ggl.cloud.domain.Music;
// import com.ggl.cloud.domain.User;
// import com.ggl.cloud.domain.Video;
// import org.apache.ibatis.annotations.Mapper;
// import org.apache.ibatis.annotations.Param;

// import java.util.List;

// @Mapper
// public interface ServerMapper {
//     List<Note> getNotes(String userId);

//     List<Note> selectNotes(Note note);

//     Integer updateNote(Note note);

//     User selectUser(String userId);

//     Integer insertUser(@Param("userId") String userId, @Param("password") String password, @Param("uuid") String uuid);

//     Integer insertNote(Note note);

//     Integer deleteNote(String uuid);

//     List<Music> selectMusic(String userId);

//     Music changeMusic(String uuid);

//     List<Music> selectMusicParam(Music music);

//     Integer uploadMusic(Music music);

//     Integer deleteMusic(String uuid);

//     Integer uploadVideo(Video video);

//     List<Video> searchVideo(@Param("userId") String userId, @Param("videoName") String videoName);

//     Integer deleteVideo(String uuid);
// }
