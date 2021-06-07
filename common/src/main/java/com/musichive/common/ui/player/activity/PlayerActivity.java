package com.musichive.common.ui.player.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.player.PlayingInfoManager;
import com.kunminx.player.bean.dto.ChangeMusic;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.app.BaseStatusBarActivity;
import com.musichive.common.bean.music.GoodsPlayerBean;
import com.musichive.common.bean.music.MusicLibPlayerBean;
import com.musichive.common.bean.music.NFTPlayerBean;
import com.musichive.common.bean.music.TestAlbum;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.ui.player.adapter.PlayerBannerAdapter;
import com.musichive.common.ui.player.viewmodel.PlayerViewModel;
import com.musichive.common.ui.player.weight.PlayerListView;
import com.musichive.common.utils.HandlerUtils;
import com.musichive.common.utils.LogUtils;
import com.musichive.common.utils.ProgressTimeUtils;
import com.musichive.common.utils.ViewMapUtils;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 11:14
 * Description 乐库和市集播放器
 */
public class PlayerActivity extends BaseStatusBarActivity {

    private PlayerViewModel playerViewModel;
    private PlayerBannerAdapter playerBannerAdapter;
    private Banner banner;
    private PlayerListView playerListView;

    @Override
    protected void initViewModel() {
        playerViewModel = getActivityScopeViewModel(PlayerViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        playerBannerAdapter = new PlayerBannerAdapter(this, playerViewModel.playList.get());
        return new DataBindingConfig(R.layout.common_activity_player, BR.viewModel, playerViewModel)
                .addBindingParam(BR.clickProxy, new ClickProxy())
                .addBindingParam(BR.listener, listener)
                .addBindingParam(BR.listenerClick, listenerClick)
                .addBindingParam(BR.adapterBanner, playerBannerAdapter);
    }

    @Override
    protected View getTitleBar() {
        return findViewById(R.id.ll_title_bar);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ViewMapUtils.clearCacheView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        banner = findViewById(R.id.banner);
        PlayerManager.getInstance().getPauseLiveData().observe(this, aBoolean -> {
            playerViewModel.isPlaying.set(!aBoolean);
            playerBannerAdapter.playAnim(!aBoolean);
            if (playerListView != null) {
                playerListView.playerListAdapter.notifyDataSetChanged();
            }
        });
        PlayerManager.getInstance().getClearPlayListLiveData().observe(this, aBoolean -> {
            if (aBoolean) {
                finish();
            }
        });
        PlayerManager.getInstance().getPlayDataLiveData().observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object result) {
                //播放时请求接口获取到数据回调
                LogUtils.e("网络访问结束-去更新数据" + result);
                if (result instanceof GoodsPlayerBean) {
                    GoodsPlayerBean goodsPlayerBean = (GoodsPlayerBean) result;
                    playerViewModel.lrcText.set(goodsPlayerBean.getLyric());
                    playerViewModel.authorName.set("");
                    playerViewModel.showInfoTypeViewYear.set(goodsPlayerBean.getSellFormStr());
                    playerViewModel.showInfoTypeViewTime.set(goodsPlayerBean.getConfTypeIdStr());
                    playerViewModel.musicGenreName.set(goodsPlayerBean.getGenreStr());
                    playerViewModel.showInfoTypeView.set(true);
                } else if (result instanceof MusicLibPlayerBean) {
                    MusicLibPlayerBean musicLibPlayerBean = (MusicLibPlayerBean) result;
                    playerViewModel.lrcText.set(musicLibPlayerBean.getLyric_url());
                    playerViewModel.authorName.set("");
                    playerViewModel.songName.set("");
                    playerViewModel.showInfoTypeViewYear.set("");
                    playerViewModel.showInfoTypeViewTime.set("");
                    playerViewModel.musicGenreName.set("");
                    playerViewModel.showInfoTypeView.set(false);
                } else if (result instanceof NFTPlayerBean) {
                    NFTPlayerBean nftPlayerBean = (NFTPlayerBean) result;
                    playerViewModel.lrcText.set(nftPlayerBean.getLyric());
                    playerViewModel.authorName.set(nftPlayerBean.getCreaterName());
                    playerViewModel.songName.set(nftPlayerBean.getNftName());
                    playerViewModel.musicGenreName.set("");
                    playerViewModel.showInfoTypeViewYear.set("");
                    playerViewModel.showInfoTypeViewTime.set("");
                    playerViewModel.showInfoTypeView.set(false);
                }
            }
        });
        PlayerManager.getInstance().getChangeMusicLiveData().observe(this, new Observer<ChangeMusic>() {
            @Override
            public void onChanged(ChangeMusic changeMusic) {
                playerViewModel.songName.set(changeMusic.getTitle());
                playerViewModel.authorName.set("");
                playerViewModel.lrcText.set("");
                if (playerListView != null) {
                    playerListView.updateInfo();
                }
                if (runnable.position != -1 && runnable.position != PlayerManager.getInstance().getAlbumIndex()) {
                    isScrolledSetting=true;
                    banner.setCurrentItem(PlayerManager.getInstance().getAlbumIndex() + 1, false);
                }
            }
        });
        PlayerManager.getInstance().getPlayingMusicLiveData().observe(this, playingMusic -> {
            // 播放进度 状态的改变
            playerViewModel.maxSeekDuration.set(playingMusic.getDuration());
            playerViewModel.currentSeekPosition.set(playingMusic.getPlayerPosition());
            playerViewModel.currentStr.set(ProgressTimeUtils.formatTimeIntervalMinSec(playingMusic.getPlayerPosition()));
            playerViewModel.maxStr.set(ProgressTimeUtils.formatTimeIntervalMinSec(playingMusic.getDuration()));
        });
        PlayerManager.getInstance().getPlayModeLiveData().observe(this, new Observer<Enum>() {
            @Override
            public void onChanged(Enum anEnum) {
                PlayingInfoManager.RepeatMode mode = (PlayingInfoManager.RepeatMode) anEnum;
                playerViewModel.setModeSrc(mode);
                if (playerListView != null) {
                    playerListView.updateInfo();
                }
            }
        });
    }

    private OnBannerListener listenerClick = new OnBannerListener<TestAlbum.TestMusic>() {

        @Override
        public void OnBannerClick(TestAlbum.TestMusic data, int position) {
            playerViewModel.showBannerAndLrcView.set(false);
        }
    };

    //防止点击下一曲过快，动画闪烁问题
    private boolean isScrolled;
    private boolean isScrolledSetting=false;

    private OnPageChangeListener listener = new OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            int index = PlayerManager.getInstance().getAlbumIndex();
            if (position == index || index == -1 || position == -1) {
                return;
            }
            HandlerUtils.getInstance().getWorkHander().removeCallbacks(runnable);
            runnable.position = position;
            HandlerUtils.getInstance().getWorkHander().post(runnable);
        }

        @Override
        public void onPageSelectedAnyTme(int position) {
            if (isScrolledSetting){
                onPageSelected(position);
            }
            isScrolledSetting=false;

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager2.SCROLL_STATE_DRAGGING || state == ViewPager2.SCROLL_STATE_SETTLING) {
                isScrolled = true;
            } else {
                //滑动闲置或滑动结束
                isScrolled = false;
            }
        }

    };

    private PositionRunnable runnable = new PositionRunnable();

    private static class PositionRunnable implements Runnable {
        public int position = -1;

        public PositionRunnable() {
        }

        @Override
        public void run() {
            PlayerManager.getInstance().playAudio(position);
        }
    }

    public static class SeekBarChangeHandler implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            PlayerManager.getInstance().setSeek(seekBar.getProgress());
        }
    }

    public class ClickProxy {

        public void changeMode() {
            PlayerManager.getInstance().changeMode();
        }

        public void playPrevious() {
            playNextAndPrevious(false);
        }

        public void play() {
            if (PlayerManager.getInstance().isPlaying()) {
                PlayerManager.getInstance().pauseAudio();
            } else {
                PlayerManager.getInstance().playAudio();
            }
        }

        public void playNext() {
            playNextAndPrevious(true);
        }

        public void playList() {
            if (playerListView == null) {
                playerListView = new PlayerListView(PlayerActivity.this);
            }
            playerListView.showAtLocation();
        }

        public void clickLrcView() {
            playerViewModel.showBannerAndLrcView.set(true);
        }

        public void playNextAndPrevious(boolean isNext) {
            if (isScrolled) {
                return;
            }
            int count = banner.getItemCount();
            if (count == 0) {
                return;
            }
            int currentItem = banner.getCurrentItem();
            int next = playerViewModel.playNextAndPrevious(isNext, currentItem, count);
            isScrolledSetting=true;
            banner.setCurrentItem(next, Math.abs(next - currentItem) == 1);//随机播放就不给动画了
        }

    }
}
