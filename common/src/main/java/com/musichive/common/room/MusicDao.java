package com.musichive.common.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 11:44
 * Description 音乐蜜蜂-mvvm版本
 */
@Dao
public interface MusicDao {
    @Insert
    void insertMusic(MusicEntity musicEntity);

    @Insert
    void insertMusics(List<MusicEntity> musicEntityList);

    @Delete
    void deleteMusic(MusicEntity musicEntity);

    @Update
    void updateMusic(MusicEntity musicEntity);

    @Query("DELETE FROM music_table WHERE musicId = :musicId")
    void deleteMusic(String musicId);

    @Query("DELETE FROM music_table")
    void deleteMusicAll();

    @Query("SELECT * FROM music_table")
    List<MusicEntity> getMusicList();

    @Query("SELECT * FROM music_table WHERE musicId = :musicId")
    MusicEntity getStudentById(String musicId);

}
