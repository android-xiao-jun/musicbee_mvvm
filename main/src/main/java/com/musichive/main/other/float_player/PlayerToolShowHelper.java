package com.musichive.main.other.float_player;

import android.view.ViewGroup;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 10:50
 * Description 只要实现这个接口才显示
 */
public interface PlayerToolShowHelper {


    default ViewGroup getBottomView() {
        return null;
    }

}
