package com.musichive.common.other.float_player;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.Observer;

import com.kunminx.player.bean.dto.ChangeMusic;
import com.kunminx.player.bean.dto.PlayingMusic;
import com.musichive.common.player.PlayerManager;


/**
 * @Author Jun
 * Date 2021年5月31日15:34:11
 * Description 旋转播放view 顶部
 */
public final class PlayerTool implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "FPSTool";
    public static PlayerTool mInstance;

    private Application application;

    public static PlayerTool getInstance() {
        if (mInstance == null) {
            synchronized (PlayerTool.class) {
                if (mInstance == null) {
                    mInstance = new PlayerTool();
                }
            }
        }
        return mInstance;
    }

    public void start(Application c) {
        this.application = c;
        application.registerActivityLifecycleCallbacks(this);
        Log.e(TAG, "start");
        PlayerToolFloatUtils.get().add();
        PlayerToolFloatUtils.get().initFpsLayout();
        PlayerManager.getInstance().getChangeMusicLiveData().observeForever(changeMusic);
        PlayerManager.getInstance().getPlayingMusicLiveData().observeForever(playingMusic);
        PlayerManager.getInstance().getPauseLiveData().observeForever(aBoolean);
        PlayerManager.getInstance().getPlayModeLiveData().observeForever(anEnum);
    }

    public void stop() {
        Log.e(TAG, "stop");
        PlayerToolFloatUtils.get().remove();
        PlayerManager.getInstance().getChangeMusicLiveData().removeObserver(changeMusic);
        PlayerManager.getInstance().getPlayingMusicLiveData().removeObserver(playingMusic);
        PlayerManager.getInstance().getPauseLiveData().removeObserver(aBoolean);
        PlayerManager.getInstance().getPlayModeLiveData().removeObserver(anEnum);
        application.unregisterActivityLifecycleCallbacks(null);
        application = null;
    }

    private Observer<ChangeMusic> changeMusic = new Observer<ChangeMusic>() {
        @Override
        public void onChanged(ChangeMusic changeMusic) {
            // 切歌时，音乐的标题、作者、封面 状态的改变
            PlayerToolFloatUtils.get().loadPic(changeMusic.getImg());
        }
    };

    private Observer<PlayingMusic> playingMusic = new Observer<PlayingMusic>() {
        @Override
        public void onChanged(PlayingMusic playingMusic) {
            // 播放进度 状态的改变
        }
    };

    private Observer<Boolean> aBoolean = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            // 播放按钮 状态的改变(aBoolean 标识 当前是否是暂停状态)
            PlayerToolFloatUtils.get().startAnimation(!aBoolean);

        }
    };

    private Observer<Enum> anEnum = new Observer<Enum>() {
        @Override
        public void onChanged(Enum anEnum) {
            // 播放模式 状态的改变
        }
    };

    public void setOnClickListener(View.OnClickListener listener) {
        PlayerToolFloatUtils.get().setOnClickListener(listener);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof PlayerToolShowHelp) {
            PlayerToolFloatUtils.get().attach(activity);
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof PlayerToolShowHelp) {
            PlayerToolFloatUtils.get().detach(activity);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

}
