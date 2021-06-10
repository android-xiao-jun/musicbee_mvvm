package com.kunminx.architecture.ui.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Author Jun
 * Date 2021 年 06月 10 日 11:14
 * Description 音乐蜜蜂-mvvm版本
 */
public abstract class LazyFragment extends BaseFragment {
    protected boolean isViewCreated;//视图是否已经创建
    protected boolean isUiVisible;//该fragment是否对用户可见

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
        return view;
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调(setUserVisibleHint已废弃  现在使用onResume和onPause),
        // 并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        boolean tempUiVisible = isUiVisible;
        if (isViewCreated && isUiVisible) {
            lazyLoadData();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUiVisible = false;

        }

    }

    public void setFragmentVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            isUiVisible = true;
            lazyLoad();
        } else {
            isUiVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isUiVisible = false;
    }

    /**
     * BEHAVIOR_SET_USER_VISIBLE_HINT
     * 兼容旧版本
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setFragmentVisibleHint(isVisibleToUser);
    }

    /**
     * BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
     * <p>
     * 新版本采用这个
     */

    @Override
    public void onResume() {
        super.onResume();
        setFragmentVisibleHint(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        setFragmentVisibleHint(false);
    }

    public void lazyLoadData() {

    }

}
