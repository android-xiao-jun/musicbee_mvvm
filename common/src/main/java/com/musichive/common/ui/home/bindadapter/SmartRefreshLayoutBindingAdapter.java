package com.musichive.common.ui.home.bindadapter;

import androidx.databinding.BindingAdapter;

import com.musichive.common.ui.home.fragment.HomeFragment;
import com.musichive.common.ui.home.fragment.NFTFragment;
import com.musichive.common.ui.home.fragment.NewestNewFragment;
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

    @BindingAdapter(value = {"setOnRefreshLoadMoreListener"}, requireAll = false)
    public static void setOnRefreshLoadMoreListener(SmartRefreshLayout smartRefreshLayout, NFTFragment.RefreshEvent refreshEvent) {
        smartRefreshLayout.setOnRefreshLoadMoreListener(refreshEvent);
    }

    @BindingAdapter(value = {"setOnRefreshLoadMoreListener"}, requireAll = false)
    public static void setOnRefreshLoadMoreListener(SmartRefreshLayout smartRefreshLayout, NewestNewFragment.RefreshEvent refreshEvent) {
        smartRefreshLayout.setOnRefreshLoadMoreListener(refreshEvent);
    }

    @BindingAdapter(value = {"autoRefresh"}, requireAll = false)
    public static void autoRefresh(SmartRefreshLayout smartRefreshLayout, Boolean autoRefresh) {
        smartRefreshLayout.autoRefresh();
    }

    @BindingAdapter(value = {"closeLoad"}, requireAll = false)
    public static void closeLoad(SmartRefreshLayout smartRefreshLayout, Boolean closeLoad) {
        smartRefreshLayout.finishLoadMore();
    }

    @BindingAdapter(value = {"closeRefresh"}, requireAll = false)
    public static void closeRefresh(SmartRefreshLayout smartRefreshLayout, Boolean closeRefresh) {
        smartRefreshLayout.finishRefresh();
    }

    @BindingAdapter(value = {"setNoMoreData"}, requireAll = false)
    public static void setNoMoreData(SmartRefreshLayout smartRefreshLayout, Boolean noMoreData) {
        smartRefreshLayout.setNoMoreData(noMoreData);
    }
}
