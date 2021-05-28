package com.musichive.common.ui.home.activity;

import android.os.Bundle;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.app.BaseStatusBarActivity;
import com.musichive.common.config.AppConfig;
import com.musichive.common.ui.home.adapter.HomeFragmentPageAdapter;
import com.musichive.common.ui.home.fragment.HomeFragment;
import com.musichive.common.ui.home.fragment.MarketFragment;
import com.musichive.common.ui.home.fragment.MeFragment;
import com.musichive.common.ui.home.fragment.NFTFragment;
import com.musichive.common.ui.home.fragment.WorksFragment;
import com.musichive.common.ui.home.repository.HomeDataRepository;
import com.musichive.common.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 16:02
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeActivity extends BaseStatusBarActivity {

    private HomeViewModel homeViewModel;
    private HomeFragmentPageAdapter homeFragmentPageAdapter;
    private List<Fragment> mList = new ArrayList<>();

    @Override
    protected void initViewModel() {
        homeViewModel = getActivityScopeViewModel(HomeViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        mList.add(new HomeFragment());
        mList.add(new NFTFragment());
        mList.add(new MarketFragment());
        mList.add(new WorksFragment());
        mList.add(new MeFragment());
        homeFragmentPageAdapter = new HomeFragmentPageAdapter(getSupportFragmentManager(), mList);
        return new DataBindingConfig(R.layout.common_activity_home, BR.viewModel, homeViewModel)
                .addBindingParam(BR.adapter, homeFragmentPageAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (AppConfig.URLPREFIX == null) {
            HomeDataRepository.getInstance().obtainImageUrlPrefix();
        }
    }
}