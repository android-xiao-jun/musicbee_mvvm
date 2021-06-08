package com.musichive.common.ui.home.fragment;

import android.view.View;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.app.BaseStatusBarFragment;
import com.musichive.common.ui.home.viewmodel.MusicLibFragmentViewModel;


/**
 * @Author Jun
 * Date 2021 年 06月 08 日 13:33
 * Description 音乐蜜蜂-mvvm版本
 */
public class MusicLibFragment extends BaseStatusBarFragment {
    private MusicLibFragmentViewModel libFragmentViewModel;

    @Override
    protected void initViewModel() {
        libFragmentViewModel = getFragmentScopeViewModel(MusicLibFragmentViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.common_fragment_music_lib, BR.viewModel, libFragmentViewModel);
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    public View getTitleBar() {
        return getView().findViewById(R.id.ll_title);
    }
}
