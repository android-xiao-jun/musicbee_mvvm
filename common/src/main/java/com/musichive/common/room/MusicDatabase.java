package com.musichive.common.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 12:20
 * Description 播放列表数据库
 */
@Database(entities = {MusicEntity.class}, version = 2)
public abstract class MusicDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "music_db";

    private static MusicDatabase INSTANCE;

    public static synchronized MusicDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), MusicDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public abstract MusicDao musicDao();
}
