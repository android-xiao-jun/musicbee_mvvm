package com.musichive.common.ui.player.activity;

import android.os.Bundle;

import androidx.lifecycle.Observer;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.app.BaseApplication;
import com.musichive.common.app.BaseStatusBarActivity;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.room.MusicDatabase;
import com.musichive.common.ui.player.viewmodel.PlayerViewModel;
import com.musichive.common.utils.HandlerUtils;

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
        return new DataBindingConfig(R.layout.common_activity_player, BR.viewModel, playerViewModel)
                .addBindingParam(BR.clickProxy, new ClickProxy());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PlayerManager.getInstance().getPauseLiveData().observe(this, aBoolean -> {
            playerViewModel.isPlaying.set(!aBoolean);
            playerViewModel.notifyChange();
        });
        PlayerManager.getInstance().getClearPlayListLiveData().observe(this, aBoolean -> {
            if (aBoolean){
                finish();
            }
        });
    }

    public class ClickProxy {

        public void playPrevious() {
            HandlerUtils.getInstance().postWork(() -> {
                PlayerManager.getInstance().playPrevious();
            });
        }

        public void play() {
            HandlerUtils.getInstance().postWork(() -> {
                if (playerViewModel.isPlaying.get()) {
                    PlayerManager.getInstance().pauseAudio();
                } else {
                    PlayerManager.getInstance().playAudio();
                }
            });
        }

        public void playNext() {
            HandlerUtils.getInstance().postWork(() -> {
                PlayerManager.getInstance().playNext();
            });
        }

        public void playClear() {
            HandlerUtils.getInstance().postWork(() -> {
                PlayerManager.getInstance().clear();
                MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().deleteMusicAll();
            });
        }
    }
}
