package com.musichive.common.ui.home.bindadapter;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.musichive.base.wight.NoScrollViewPager;
import com.musichive.common.weight.HomeBottomNav;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 17:50
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeBindingAdapter {
    @BindingAdapter(value = {"setAdapter"}, requireAll = true)
    public static void setAdapter(NoScrollViewPager viewPager, PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter(value = {"initTabAndPage"}, requireAll = false)
    public static void initTabAndPage(HomeBottomNav homeBottomNav, boolean initTabAndPage) {
        homeBottomNav.initTabAndPage();
    }

}
