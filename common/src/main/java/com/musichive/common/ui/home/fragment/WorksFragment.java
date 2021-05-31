package com.musichive.common.ui.home.fragment;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.app.BaseStatusBarFragment;
import com.musichive.common.ui.home.viewmodel.NFTFragmentViewModel;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:48
 * Description 音乐蜜蜂-mvvm版本
 */
public class WorksFragment extends BaseStatusBarFragment {

    private NFTFragmentViewModel homeFragmentViewModel;

    @Override
    protected void initViewModel() {
        homeFragmentViewModel = getFragmentScopeViewModel(NFTFragmentViewModel.class);
        homeFragmentViewModel.indexStr.set("works");
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.common_fragment_works, BR.viewModel,homeFragmentViewModel);
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }
}
