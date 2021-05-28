package com.musichive.common.ui.home.bindadapter;

import androidx.databinding.BindingAdapter;

import com.musichive.common.ui.home.fragment.HomeFragment;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 17:46
 * Description 音乐蜜蜂-mvvm版本
 */
public class SmartRefreshLayoutBindingAdapter {

    @BindingAdapter(value = {"setOnRefreshLoadMoreListener"}, requireAll = false)
    public static void setOnRefreshLoadMoreListener(SmartRefreshLayout smartRefreshLayout, HomeFragment.RefreshEvent refreshEvent) {
        smartRefreshLayout.setOnRefreshLoadMoreListener(refreshEvent);
    }

    @BindingAdapter(value = {"autoRefresh"}, requireAll = false)
    public static void autoRefresh(SmartRefreshLayout smartRefreshLayout, Boolean autoRefresh) {
        smartRefreshLayout.autoRefresh();
    }
}
