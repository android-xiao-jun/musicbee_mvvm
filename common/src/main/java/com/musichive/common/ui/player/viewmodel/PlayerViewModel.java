package com.musichive.common.ui.player.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kunminx.player.PlayingInfoManager;
import com.musichive.common.R;
import com.musichive.common.app.BaseViewModel;
import com.musichive.common.bean.music.TestAlbum;
import com.musichive.common.player.PlayerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 10:40
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerViewModel extends ViewModel {

    public ObservableBoolean isPlaying = new ObservableBoolean();
    public ObservableInt modeSrc = new ObservableInt();
    public ObservableField<String> songName = new ObservableField<>();
    public ObservableField<String> authorName = new ObservableField<>();
    public ObservableField<String> lrcText = new ObservableField<>();

    public final ObservableInt maxSeekDuration = new ObservableInt();
    public final ObservableInt currentSeekPosition = new ObservableInt();
    public final ObservableBoolean smoothScroll = new ObservableBoolean();

    public final ObservableField<String> maxStr = new ObservableField();
    public final ObservableField<String> currentStr = new ObservableField();

    public ObservableField<List<TestAlbum.TestMusic>> playList = new ObservableField<>();

    public MutableLiveData<PlayingInfoManager.RepeatMode> modeLiveData = new MutableLiveData<>();

    public PlayerViewModel() {
        playList.set(PlayerManager.getInstance().getAlbumMusics());
        smoothScroll.set(false);
        setModeSrc((PlayingInfoManager.RepeatMode) PlayerManager.getInstance().getRepeatMode());
        maxStr.set("00:00");
        currentStr.set("00:00");
    }

    public String getPlayString() {
        return isPlaying.get() ? "暂停" : "播放";
    }

    public boolean isPlayer() {
        return isPlaying.get();
    }

    public void setModeSrc(PlayingInfoManager.RepeatMode mode) {
        if (mode == PlayingInfoManager.RepeatMode.LIST_CYCLE) {
            modeSrc.set(R.drawable.ic_listrecycle_mid);
        } else if (mode == PlayingInfoManager.RepeatMode.RANDOM) {
            modeSrc.set(R.drawable.ic_random_mid);
        } else if (mode == PlayingInfoManager.RepeatMode.SINGLE_CYCLE) {
            modeSrc.set(R.drawable.ic_singlerecycle_mid);
        } else {
            modeSrc.set(R.drawable.ic_listrecycle_mid);
        }
        modeLiveData.postValue(mode);
    }

}
