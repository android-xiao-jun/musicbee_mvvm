package com.musichive.main.ui.home.fragment;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.player.bean.dto.PlayingMusic;
import com.kunminx.player.helper.MediaPlayerHelper;
import com.musichive.main.BR;
import com.musichive.main.R;
import com.musichive.aop.Permissions;
import com.musichive.main.app.BaseStatusBarFragment;
import com.musichive.main.player.PlayerManager;
import com.musichive.main.ui.home.viewmodel.TempFragmentViewModel;
import com.musichive.main.utils.ProgressTimeUtils;
import com.musichive.main.weight.PlayerSeekBar;

import org.jetbrains.annotations.NotNull;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:48
 * Description 音乐蜜蜂-mvvm版本
 */
public class MeFragment extends BaseStatusBarFragment {

    private TempFragmentViewModel homeFragmentViewModel;
    private BarVisualizer mVisualizer;
    private PlayerSeekBar player_seeker_bar;

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
         player_seeker_bar = getBinding().getRoot().findViewById(R.id.player_seeker_bar);
//        player_seeker_bar.setSelectProgress(10);
        player_seeker_bar.setOnProgressChangeListener(new PlayerSeekBar.OnProgressChangeListener() {
            @Override
            public void onChange(int selectProgress, boolean formUser) {
                if (formUser){
                    PlayerManager.getInstance().setSeek(selectProgress);
                }
            }
        });
        PlayerManager.getInstance().getPlayingMusicLiveData().observe(getViewLifecycleOwner(), new Observer<PlayingMusic>() {
            @Override
            public void onChanged(PlayingMusic playingMusic) {
                player_seeker_bar.setMaxProgress(playingMusic.getDuration());
                player_seeker_bar.setSelectProgress(playingMusic.getPlayerPosition());
                homeFragmentViewModel.indexStr.set(ProgressTimeUtils.formatTimeIntervalMinSec(playingMusic.getPlayerPosition()));
            }
        });
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        requestPermissions();
    }

    @Permissions({Manifest.permission.RECORD_AUDIO})
    private void requestPermissions(){
        mVisualizer = getBinding().getRoot().findViewById(R.id.blast);
        int audioSessionId = MediaPlayerHelper.getInstance().getMediaPlayer().getAudioSessionId();
        if (audioSessionId != -1) {
            mVisualizer.setAudioSessionId(audioSessionId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mVisualizer != null) {
            mVisualizer.show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVisualizer != null) {
            mVisualizer.hide();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mVisualizer != null) {
            mVisualizer.release();
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }
}
