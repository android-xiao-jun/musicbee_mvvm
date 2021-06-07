package com.musichive.common.utils;

import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;

import com.musichive.common.R;

import java.util.Map;

/**
 * @Author Jun
 * Date 2021 年 06月 07 日 10:17
 * Description 音乐蜜蜂-mvvm版本
 */
public class ViewMapUtils {
    public static Map<String, View> saveView;

    public static View getCacheView(Context context, String key) {
        if (saveView == null) {
            saveView = new ArrayMap<>();
        }
        View view = saveView.get(key);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.player_music_type, null, false);
            saveView.put(key, view);
        }
        return view;
    }

    public static void clearCacheView() {
        if (saveView != null) {
            saveView.clear();
        }
        saveView = null;
    }
}
