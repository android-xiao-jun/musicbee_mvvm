package com.musichive.common.ui.player.activity;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.app.BaseStatusBarActivity;
import com.musichive.common.ui.player.viewmodel.PlayerViewModel;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 11:14
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerActivity extends BaseStatusBarActivity {

    private PlayerViewModel playerViewModel;

    @Override
    protected void initViewModel() {
        playerViewModel = getActivityScopeViewModel(PlayerViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.common_activity_player, BR.viewModel, playerViewModel);
    }
}
