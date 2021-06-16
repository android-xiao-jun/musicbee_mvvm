package com.musichive.main.ui.player.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.RequestBuilder;
import com.kunminx.player.PlayingInfoManager;
import com.musichive.base.glide.GlideUtils;
import com.musichive.main.R;
import com.musichive.main.api.RetrofitApi;
import com.musichive.main.app.AppSaveUtils;
import com.musichive.main.bean.music.MusicLibPlayerBean;
import com.musichive.main.bean.music.TestAlbum;
import com.musichive.main.config.AppConfig;
import com.musichive.main.player.PlayerManager;
import com.musichive.main.utils.HandlerUtils;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.logging.Handler;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 10:40
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerViewModel extends AndroidViewModel {

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

    public PlayerViewModel(Application application) {
        super(application);
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

    //获取证书 真实图片地址  （写成这样，没做抽取，也就这里一个地方）
    public MutableLiveData<String> getPicUrl() {
        MutableLiveData<String> stringMutableLiveData = new MutableLiveData<>();
        MutableLiveData<Object> playDataLiveData = PlayerManager.getInstance().getPlayDataLiveData();
        if (playDataLiveData.getValue() instanceof MusicLibPlayerBean) {
            HandlerUtils.getInstance().postWork(() -> {
                MusicLibPlayerBean libPlayerBean = (MusicLibPlayerBean) playDataLiveData.getValue();
                String author = "";
                if (!TextUtils.isEmpty(libPlayerBean.getAuthor())) {
                    author = libPlayerBean.getAuthor();
                } else {
                    author = libPlayerBean.getAccount();
                }
                final String url = AppConfig.NetWork.BASE_WEB_PAGE_URL + "index/musicFileurl?music_id=" + libPlayerBean.getRelate_id()
                        + "&type=" + libPlayerBean.getConf_type_id()
                        + "&account=" + author;

                String value = AppSaveUtils.getInstance().getString(url);
                if (!TextUtils.isEmpty(value)) {
                    stringMutableLiveData.postValue(value);
                    return;
                }
                RetrofitApi.OkHttpUtils.requestGetUrl(url, new RetrofitApi.CallBack() {
                    @Override
                    public void onResponse(String str) {
                        JSONObject jsonObject = JSON.parseObject(str);
                        String data = jsonObject.getString("data");
                        if (!TextUtils.isEmpty(data)) {
                            GlideUtils.preload(getApplication(), data);
                            AppSaveUtils.getInstance().saveString(url, data);
                            stringMutableLiveData.postValue(data);
                        } else {
                            stringMutableLiveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        stringMutableLiveData.postValue(null);
                    }
                });
            });
        } else {
            stringMutableLiveData.postValue(null);
        }
        return stringMutableLiveData;
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
