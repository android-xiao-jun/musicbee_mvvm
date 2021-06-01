package com.musichive.common.room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 11:40
 * Description 保存数据--暂时先存这些，后面要再加（每条音乐播放数据）
 */
@Entity(tableName = "music_table")
public class MusicEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    public String name;//作者名称

    @ColumnInfo(name = "musicId", typeAffinity = ColumnInfo.TEXT)
    public String musicId;//音乐id（goodsId 或者 permlink 或者 nftid）

    @ColumnInfo(name = "coverImg", typeAffinity = ColumnInfo.TEXT)
    public String coverImg;//封面图

    @ColumnInfo(name = "url", typeAffinity = ColumnInfo.TEXT)
    public String url;// 可以直接播放的链接 m3u8

    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    public String title;//demo 歌曲 名称

    public MusicEntity() {
    }
}
