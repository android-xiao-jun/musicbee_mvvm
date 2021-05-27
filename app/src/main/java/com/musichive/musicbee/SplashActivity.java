package com.musichive.musicbee;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.kunminx.architecture.ui.page.BaseActivity;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.ui.home.HomeActivity;
import com.musichive.musicbee.viewmodel.SplashViewModel;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 15:42
 * Description 音乐蜜蜂-mvvm版本
 */
public class SplashActivity extends BaseActivity {

    private SplashViewModel splashViewModel;

    @Override
    protected void initViewModel() {
        splashViewModel = getActivityScopeViewModel(SplashViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.app_activity_splash, BR.viewModel, splashViewModel);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, HomeActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }
}
