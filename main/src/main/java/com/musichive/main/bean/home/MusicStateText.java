package com.musichive.main.bean.home;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 15:19
 * Description 音乐蜜蜂-mvvm版本
 */
public class MusicStateText {
    public String id="";
    public HomeDynamicInfo.ListBean item;

    public MusicStateText() {
    }

    public MusicStateText(HomeDynamicInfo.ListBean item) {
        this.item = item;
    }
}
