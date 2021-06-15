package com.musichive.common.ui.home.fragment;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.app.BaseStatusBarFragment;
import com.musichive.common.ui.home.viewmodel.TempFragmentViewModel;
import com.musichive.common.weight.PlayerSeekBar;

import org.jetbrains.annotations.NotNull;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:48
 * Description 音乐蜜蜂-mvvm版本
 */
public class MeFragment extends BaseStatusBarFragment {

    private TempFragmentViewModel homeFragmentViewModel;

    @Override
    protected void initViewModel() {
        homeFragmentViewModel = getFragmentScopeViewModel(TempFragmentViewModel.class);
        homeFragmentViewModel.indexStr.set("me");
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.common_fragment_me, BR.viewModel, homeFragmentViewModel);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //这里只是演示，，不要直接findviewbyid
        PlayerSeekBar player_seeker_bar = getBinding().getRoot().findViewById(R.id.player_seeker_bar);
//        player_seeker_bar.setSelectProgress(10);
        player_seeker_bar.setOnProgressChangeListener(new PlayerSeekBar.OnProgressChangeListener() {
            @Override
            public void onChange(int selectProgress, boolean formUser) {
                homeFragmentViewModel.indexStr.set("" + selectProgress);
            }
        });
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }
}
