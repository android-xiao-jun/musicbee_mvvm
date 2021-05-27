package com.musichive.common.ui.home;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.app.BaseStatusBarActivity;
import com.musichive.common.viewmodel.HomeViewModel;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 16:02
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeActivity extends BaseStatusBarActivity {

    private HomeViewModel homeViewModel;

    @Override
    protected void initViewModel() {
        homeViewModel = getActivityScopeViewModel(HomeViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.common_activity_home, BR.viewModel, homeViewModel);
    }
}
