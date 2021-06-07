package com.musichive.common.bean.music;

import android.text.TextUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 07 日 09:41
 * Description 音乐蜜蜂-mvvm版本
 */

public class GoodsPlayerBean implements Serializable {

    /**
     * id : 1252
     * musicId : 59452
     * permlink : pixbe-musicmall-m74220145-1622801110227
     * title : 失旧
     * lyric : 失旧

     还是没敢 翻开那一次相册
     总以为不见 就记不起 那些苦涩
     是我不 甘心躲在暗处作恶
     还是后悔了 当初 做的取舍

     我真的以为 尽量不去想起你
     就能够忘怀 从前那段 水深火热
     可当我 再次听见那首老歌
     所有都形同虚设 

     回忆 总是 不断 在重复 播放
     溢出 感伤 该寄 存于何处藏
     当衷情 成牵绊 当牵念 成空想
     不甘 也随着冷风 四处流亡

     可是 我们 后来 有多少 难堪
     连歌词都在 嘲讽 情绪 敏感
     越难忘 越向往 越遗憾 越奢望
     旧事掀起的波澜 让眼泪 放弃抵抗
     * cover : default-img/161.jpg
     * musicEncodeUrl : https://1331-cn-north-4.cdn-vod.huaweicloud.com/asset/082a4cdc4cc74d281c7defa559987a59/play_video/0b87d7f3de82362673007346c01d8f61.m3u8
     * genre : 4,5
     * duration : 75.20653061
     * genreList : null
     * collectionFlag : 0
     * playFlag : 1
     * playMsg : 
     * status : 1
     * account : m74220145
     * tags : 17,15,11
     * tagsList : [{"name":"曲风","id":1,"list":[{"name":"流行","id":"17"}]},{"name":"情绪","id":3,"list":[{"name":"中性","id":"15"}]},{"name":"人声","id":2,"list":[{"name":"男声","id":"11"}]}]
     * confTypeId : 8
     * sellto : null
     * sellForm : 1
     * term : 0
     */

    public int id;
    public int musicId;
    public String permlink;
    public String title;
    public String lyric;
    public String cover;
    public String musicEncodeUrl;
    public String genre;
    public double duration;
    public List<GenreListBean> genreList;
    public int collectionFlag;
    public int playFlag;
    public String playMsg;
    public int status;
    public String account;
    public String tags;
    public String confTypeId;
    public Object sellto;
    public String sellForm;
    public int term;
    public List<TagsListBean> tagsList;

    
    public static class TagsListBean implements Serializable {
        /**
         * name : 曲风
         * id : 1
         * list : [{"name":"流行","id":"17"}]
         */

        public String name;
        public int id;
        public List<ListBean> list;

        
        public static class ListBean implements Serializable {
            /**
             * name : 流行
             * id : 17
             */

            public String name;
            public String id;
        }
    }

    public static class GenreListBean{
        public String title;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }

    public String getPermlink() {
        return permlink;
    }

    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getMusicEncodeUrl() {
        return musicEncodeUrl;
    }

    public void setMusicEncodeUrl(String musicEncodeUrl) {
        this.musicEncodeUrl = musicEncodeUrl;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public List<GenreListBean> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<GenreListBean> genreList) {
        this.genreList = genreList;
    }

    public int getCollectionFlag() {
        return collectionFlag;
    }

    public void setCollectionFlag(int collectionFlag) {
        this.collectionFlag = collectionFlag;
    }

    public int getPlayFlag() {
        return playFlag;
    }

    public void setPlayFlag(int playFlag) {
        this.playFlag = playFlag;
    }

    public String getPlayMsg() {
        return playMsg;
    }

    public void setPlayMsg(String playMsg) {
        this.playMsg = playMsg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getConfTypeId() {
        return confTypeId;
    }

    public void setConfTypeId(String confTypeId) {
        this.confTypeId = confTypeId;
    }

    public Object getSellto() {
        return sellto;
    }

    public void setSellto(Object sellto) {
        this.sellto = sellto;
    }

    public String getSellForm() {
        return sellForm;
    }

    public void setSellForm(String sellForm) {
        this.sellForm = sellForm;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public List<TagsListBean> getTagsList() {
        return tagsList;
    }

    public void setTagsList(List<TagsListBean> tagsList) {
        this.tagsList = tagsList;
    }

    public String getSellFormStr() {
        if(TextUtils.equals("1",sellForm)){
            return "永久转让";
        }else if(TextUtils.equals("2",sellForm)){
            if(term==0){
                return "终生授权";
            }else{
                return term+"年授权";
            }
        }
        return "永久转让";
    }

    public String getConfTypeIdStr() {
        if(TextUtils.equals("1",confTypeId)){
            return "歌曲";
        }else if(TextUtils.equals("3",confTypeId)){
            return "歌词";
        }else if(TextUtils.equals("8",confTypeId)){
            return "demo";
        }
        return "demo";
    }

    public String getGenreStr(){
        StringBuilder genreSb = new StringBuilder();
        if (getGenreList() != null && getGenreList().size() > 0) {
            for (int i = 0; i < getGenreList().size(); i++) {
                if (i == 0) {
                    genreSb.append(getGenreList().get(i).getTitle());
                } else {
                    genreSb.append(",");
                    genreSb.append(getGenreList().get(i).getTitle());
                }
            }
        }
        return genreSb.toString();
    }
}
