package com.musichive.common.ui.player.viewmodel;

import androidx.databinding.ObservableBoolean;

import com.musichive.common.app.BaseViewModel;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 10:40
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerViewModel extends BaseViewModel {

    public ObservableBoolean isPlaying = new ObservableBoolean();

    public String getPlayString() {
        return isPlaying.get() ? "暂停" : "播放";
    }

}
