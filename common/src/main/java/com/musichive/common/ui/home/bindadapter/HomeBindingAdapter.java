package com.musichive.common.ui.home.bindadapter;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.musichive.base.wight.NoScrollViewPager;
import com.musichive.common.test.BaseItemAdapter;
import com.musichive.common.ui.home.weight.HomeTopView;
import com.musichive.common.weight.HomeBottomNav;

import java.util.List;

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

    @BindingAdapter(value = {"setAdapter","setList"}, requireAll = false)
    public static void setAdapter(RecyclerView recyclerView, BaseItemAdapter adapter, List<Object> mList) {
        adapter.setDataItems(mList);
        recyclerView.setAdapter(adapter);
    }


    @BindingAdapter(value = {"initTabAndPage"}, requireAll = false)
    public static void initTabAndPage(HomeBottomNav homeBottomNav, boolean initTabAndPage) {
        homeBottomNav.initTabAndPage();
    }
    @BindingAdapter(value = {"initTop"}, requireAll = false)
    public static void initTop(HomeTopView homeTopView, boolean initTop) {
        homeTopView.initTop();
    }

}
