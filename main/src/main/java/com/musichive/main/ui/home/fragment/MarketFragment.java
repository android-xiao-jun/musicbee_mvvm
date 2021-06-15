package com.musichive.main.ui.home.fragment;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.main.BR;
import com.musichive.main.R;
import com.musichive.main.app.BaseStatusBarFragment;
import com.musichive.main.ui.home.viewmodel.TempFragmentViewModel;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:48
 * Description 音乐蜜蜂-mvvm版本
 */
public class MarketFragment extends BaseStatusBarFragment {

    private TempFragmentViewModel homeFragmentViewModel;

    @Override
    protected void initViewModel() {
        homeFragmentViewModel = getFragmentScopeViewModel(TempFragmentViewModel.class);
        homeFragmentViewModel.indexStr.set("market");
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.common_fragment_market, BR.viewModel,homeFragmentViewModel);
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }
}
