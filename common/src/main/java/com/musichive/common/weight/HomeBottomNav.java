package com.musichive.common.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.musichive.common.databinding.CommonHomeBottomNavBinding;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 17:38
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeBottomNav extends RelativeLayout {

    private CommonHomeBottomNavBinding bottomNavBinding;

    public HomeBottomNav(Context context) {
        this(context, null);
    }

    public HomeBottomNav(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomNav(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bottomNavBinding = CommonHomeBottomNavBinding.inflate(LayoutInflater.from(context), this, true);

    }

}
