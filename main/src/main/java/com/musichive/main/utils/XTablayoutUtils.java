package com.musichive.main.utils;

import android.view.View;

import com.androidkun.xtablayout.XTabLayout;

import java.lang.reflect.Field;

/**
 * @Author Jun
 * Date 2021 年 06月 10 日 10:38
 * Description 音乐蜜蜂-mvvm版本
 */
public class XTablayoutUtils {
    //=============tablayout 指示器处理（旧代码复制过来的） start===========
    public static int[] setViewSize(XTabLayout mTabLayout, int position) {
        if (mTabLayout.getTabCount() == 0) {
            return new int[]{0, 0};
        }
        View view = getTabView(position, mTabLayout);
        int leftMargin = view.getLeft() + view.getWidth() / 2 - mTabLayout.getTabAt(position).getTextWidth() / 2;
        int textWidth = mTabLayout.getTabAt(position).getTextWidth();
        return new int[]{textWidth, leftMargin};
    }

    public static View getTabView(int index, XTabLayout mTabLayout) {
        View tabView = null;
        XTabLayout.Tab tab = mTabLayout.getTabAt(index);
        Field view = null;
        try {
            view = XTabLayout.Tab.class.getDeclaredField("mView");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        view.setAccessible(true);
        try {
            tabView = (View) view.get(tab);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return tabView;
    }
}
