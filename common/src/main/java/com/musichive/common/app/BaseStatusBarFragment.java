package com.musichive.common.app;

import android.view.View;

import androidx.annotation.NonNull;

import com.gyf.immersionbar.ImmersionBar;
import com.kunminx.architecture.ui.page.BaseFragment;
import com.kunminx.architecture.ui.page.LazyFragment;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 17:20
 * Description 状态栏沉浸Fragment
 */
public abstract class BaseStatusBarFragment extends LazyFragment {

    private ImmersionBar mImmersionBar;
    /**
     * 当前是否加载过
     */
    private boolean mLoading;

    @Override
    public void onResume() {
        super.onResume();
        initFragmentStatusBar();
    }

    protected void initFragmentStatusBar() {
        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init();
            // 设置标题栏沉浸
            if (getTitleBar() != null) {
                ImmersionBar.setTitleBar(this, getTitleBar());
            }
        }
    }

    /**
     * 是否在 Fragment 使用沉浸式
     */
    public boolean isStatusBarEnabled() {
        return false;
    }

    /**
     * 获取状态栏沉浸的配置对象
     */
    @NonNull
    protected ImmersionBar getStatusBarConfig() {
        if (mImmersionBar == null) {
            mImmersionBar = createStatusBarConfig();
        }
        return mImmersionBar;
    }

    /**
     * 初始化沉浸式
     */
    @NonNull
    protected ImmersionBar createStatusBarConfig() {
        return ImmersionBar.with(this)
                // 默认状态栏字体颜色为黑色
                .statusBarDarkFont(statusBarDarkFont())
                // 解决软键盘与底部输入框冲突问题，默认为false，还有一个重载方法，可以指定软键盘mode
                .keyboardEnable(true);
    }

    /**
     * 获取状态栏字体颜色
     */
    protected boolean statusBarDarkFont() {
        // 返回真表示黑色字体
        return true;
    }

    /**
     * 获取当前页面的titleBar（后面补通用的 可能 还有悬浮窗位置问题）
     */
    public View getTitleBar() {
        return null;
    }


}
