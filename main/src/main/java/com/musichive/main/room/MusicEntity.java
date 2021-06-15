package com.musichive.main.room;

import androidx.annotation.NonNull;
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

    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    public int id;

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    public String name;//作者名称

    @ColumnInfo(name = "account", typeAffinity = ColumnInfo.TEXT)
    public String account;//作者音乐蜜蜂id

    @PrimaryKey
    @ColumnInfo(name = "musicId", typeAffinity = ColumnInfo.TEXT)
    public @NonNull
    String musicId="";//音乐id（goodsId 或者 permlink 或者 nftid）

    @ColumnInfo(name = "coverImg", typeAffinity = ColumnInfo.TEXT)
    public String coverImg;//封面图

    @ColumnInfo(name = "url", typeAffinity = ColumnInfo.TEXT)
    public String url;// 可以直接播放的链接 m3u8

    @ColumnInfo(name = "title", typeAffinity = ColumnInfo.TEXT)
    public String title;//demo 歌曲 名称
    @ColumnInfo(name = "type", typeAffinity = ColumnInfo.INTEGER)
    public int type = -1; //0 乐库 1 市集 2 nft播放

    @ColumnInfo(name = "expand", typeAffinity = ColumnInfo.TEXT)
    public String expand = "{}"; //扩展字段==保存歌词，评论数量，等数据（防止第一次进入没数据）

    public MusicEntity() {
    }
}
