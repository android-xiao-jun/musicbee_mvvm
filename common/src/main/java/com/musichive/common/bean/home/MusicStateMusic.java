package com.musichive.common.bean.home;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 15:19
 * Description 音乐蜜蜂-mvvm版本
 */
public class MusicStateMusic {
    public String id="";

    public HomeDynamicInfo.ListBean item;

    public MusicStateMusic() {
    }

    public MusicStateMusic(HomeDynamicInfo.ListBean item) {
        this.item = item;
    }
}
