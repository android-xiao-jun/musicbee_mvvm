package com.musichive.common.other.float_player;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.annotation.IdRes;

import com.musichive.common.R;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 10:50
 * Description 只要实现这个接口才显示
 */
public interface PlayerToolShowHelp {

    //悬浮样式 顶部或者底部（底部一定要给 bindView id ）
    public static final int STYLE_TOP = 0;
    public static final int STYLE_BOTTOM = 0;

    default @IdRes
    int bindViewTop() {
        return R.id.view_tempo_float;
    }


    default int defaultStyle() {
        return STYLE_TOP;
    }

    //如果有需要动态修改可以执行这里--待实现
    default void build(Activity context) {

    }
}
