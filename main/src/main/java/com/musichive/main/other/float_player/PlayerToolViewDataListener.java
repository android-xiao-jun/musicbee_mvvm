package com.musichive.main.other.float_player;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kunminx.player.bean.dto.ChangeMusic;
import com.kunminx.player.bean.dto.PlayingMusic;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 15:05
 * Description 音乐蜜蜂-mvvm版本
 */
public abstract class PlayerToolViewDataListener extends FrameLayout {
    public PlayerToolViewDataListener(@NonNull Context context) {
        super(context);
    }

    public PlayerToolViewDataListener(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlayerToolViewDataListener(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PlayerToolViewDataListener(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public abstract void initFpsLayout();


    public abstract void setFloatClick(View.OnClickListener listener);

    public abstract void loadPic(Object pic);

    public abstract void rotateAnimation(boolean b);

    public void upData(ChangeMusic changeMusic) {

    }

    public void upDataProgress(PlayingMusic playingMusic) {

    }

    public void upPlayStatus(boolean isPlay) {

    }
}
