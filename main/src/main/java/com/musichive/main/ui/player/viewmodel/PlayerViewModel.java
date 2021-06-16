package com.musichive.main.ui.player.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kunminx.player.PlayingInfoManager;
import com.musichive.main.R;
import com.musichive.main.bean.music.TestAlbum;
import com.musichive.main.player.PlayerManager;

import java.util.List;
import java.util.Random;

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
    public ObservableField<String> likeNumText = new ObservableField<>();
    public ObservableField<String> commentNumText = new ObservableField<>();

    public final ObservableBoolean showInfoTypeView = new ObservableBoolean();
    public ObservableBoolean showBannerAndLrcView = new ObservableBoolean();
    public ObservableField<String> showInfoTypeViewTime = new ObservableField();
    public ObservableField<String> showInfoTypeViewYear = new ObservableField();
    public ObservableField<String> musicGenreName = new ObservableField();

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
        showInfoTypeView.set(false);
        showBannerAndLrcView.set(true);
        setModeSrc((PlayingInfoManager.RepeatMode) PlayerManager.getInstance().getRepeatMode());
        maxStr.set("00:00");
        currentStr.set("00:00");
        likeNumText.set("0");
        commentNumText.set("0");
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

    public int playNextAndPrevious(boolean isNext, int currentItem, int count) {
        boolean random = PlayerManager.getInstance().isRandom();
        int next = 0;
        if (random) {
            next = getBannerRandom(currentItem, count);
        } else if (isNext) {
            next = (currentItem + 1) % count;
        } else {
            next = (currentItem - 1) % count;
        }
        return next;
    }

    /**
     * 注意（这个 banner的显示位置 和 实际的播放列表 不一致 ）
     *
     * @param currentItem // banner的显示位置
     * @param count       //banner总数 不是播放列表总数
     * @return 返回下次随机播放位置的 index
     */
    private int getBannerRandom(int currentItem, int count) {
        //随机一个 [ 1 , size ] 的数 去播放
        int size = PlayerManager.getInstance().getAlbumMusics().size();
        int random = new Random().nextInt(size) + 1;
        int next = (currentItem + random) % count;
        if (next == currentItem) {
            next = (next + 1) % count;//防止随机到同一首歌曲
        }
        return next;
    }

}
