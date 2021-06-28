package com.musichive.main.other.float_player;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.ActivityUtils;
import com.kunminx.player.bean.dto.ChangeMusic;
import com.kunminx.player.bean.dto.PlayingMusic;
import com.musichive.base.glide.GlideUtils;
import com.musichive.main.R;
import com.musichive.main.databinding.ViewLayoutFloatBottomBinding;
import com.musichive.main.player.PlayerManager;
import com.musichive.main.ui.player.weight.PlayerListView;

/**
 * @Author Jun
 * Date 2021年5月31日15:25:14
 * Description 悬浮播放view
 */
public class PlayerToolTwoView extends PlayerToolViewDataListener {

    private ViewLayoutFloatBottomBinding bottomBinding;
    private ValueAnimator rotateAnimation;
    private boolean isDark;

    public PlayerToolTwoView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerToolTwoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerToolTwoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bottomBinding = ViewLayoutFloatBottomBinding.inflate(LayoutInflater.from(context), this, true);
        bottomBinding.ivPlay.setOnClickListener(v -> {
            if (PlayerManager.getInstance().isPlaying()) {
                PlayerManager.getInstance().pauseAudio();
            } else {
                PlayerManager.getInstance().playAudio();
            }
        });
        bottomBinding.ivNewFloatMiniLb.setOnClickListener(v -> {
            PlayerListView playerListView = new PlayerListView(ActivityUtils.getTopActivity());
            playerListView.showAtLocation();
        });
    }

    @Override
    public void upData(ChangeMusic changeMusic) {
        super.upData(changeMusic);
        bottomBinding.miniTextView.getSongNameView().setText(changeMusic.getTitle());
        if (!TextUtils.isEmpty(changeMusic.getArtist().getName())) {
            bottomBinding.miniTextView.getAuthorNameView().setText(" - " + changeMusic.getArtist().getName());
        } else {
            bottomBinding.miniTextView.getAuthorNameView().setText("");
        }
    }

    @Override
    public void upPlayStatus(boolean isPlay) {
        super.upPlayStatus(isPlay);
        upControlBtnBackground();
    }

    /**
     * 更新深色和浅色主题
     *
     * @param dark
     */
    public void setDark(boolean dark) {
        if (isDark == dark) {
            return;
        }
        isDark = dark;
        upDataBackground();
    }

    private void upDataBackground() {
        if (isDark) {
            bottomBinding.layout.setBackgroundColor(Color.parseColor("#232631"));
            bottomBinding.ivNewFloatMiniLb.setImageResource(R.drawable.new_float_mini_lb_w);
            bottomBinding.miniplayerProgress.setBackColor(Color.parseColor("#88FFFFFF"));
            bottomBinding.miniplayerProgress.setProgColor(Color.parseColor("#FFFFFF"));
            bottomBinding.miniTextView.getSongNameView().setTextColor(Color.parseColor("#FFFFFF"));
            bottomBinding.miniTextView.getAuthorNameView().setTextColor(Color.parseColor("#88FFFFFF"));
        } else {
            bottomBinding.layout.setBackgroundColor(Color.parseColor("#F8F8F8"));
            bottomBinding.ivNewFloatMiniLb.setImageResource(R.drawable.new_float_mini_lb);
            bottomBinding.miniplayerProgress.setBackColor(Color.parseColor("#e2e2e2"));
            bottomBinding.miniplayerProgress.setProgColor(Color.parseColor("#1e1e1e"));
            bottomBinding.miniTextView.getSongNameView().setTextColor(Color.parseColor("#1e1e1e"));
            bottomBinding.miniTextView.getAuthorNameView().setTextColor(Color.parseColor("#999999"));
        }
        upControlBtnBackground();//主题变化更新
    }

    private void upControlBtnBackground() {
        if (isDark) {
            if (PlayerManager.getInstance().isPlaying()) {
                bottomBinding.ivPlay.setImageResource(R.drawable.new_float_mini_zt_w);
            } else {
                bottomBinding.ivPlay.setImageResource(R.drawable.new_float_mini_bf_w);
            }
        } else {
            if (PlayerManager.getInstance().isPlaying()) {
                bottomBinding.ivPlay.setImageResource(R.drawable.new_float_mini_zt);
            } else {
                bottomBinding.ivPlay.setImageResource(R.drawable.new_float_mini_bf);
            }
        }
    }

    @Override
    public void initFpsLayout() {

    }

    @Override
    public void setFloatClick(OnClickListener listener) {
        bottomBinding.miniTextView.setOnClickListener(listener);
    }

    @Override
    public void loadPic(Object pic) {
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
        if (bottomBinding != null && bottomBinding.icon.getVisibility() != VISIBLE) {
            bottomBinding.icon.setVisibility(VISIBLE);
        }
        GlideUtils.loadPicToImageViewAsBitmap(getContext(), pic, bottomBinding.icon);
    }

    public Observer<Boolean> loadRunnable = new Observer<Boolean>() {

        @Override
        public void onChanged(Boolean aBoolean) {
            bottomBinding.pbLoading.setVisibility(aBoolean ? VISIBLE : GONE);
        }
    };

    public Observer<PlayingMusic> playingMusicObserver = playingMusic -> {
        // 播放进度 状态的改变
        bottomBinding.miniplayerProgress.setMaxProgress(playingMusic.getDuration());
        bottomBinding.miniplayerProgress.setProgress(playingMusic.getPlayerPosition());
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        PlayerManager.getInstance().getIsLoadPrepareAsync().observeForever(loadRunnable);
        PlayerManager.getInstance().getPlayingMusicLiveData().observeForever(playingMusicObserver);
        rotateAnimation(PlayerManager.getInstance().isPlaying());
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PlayerManager.getInstance().getIsLoadPrepareAsync().removeObserver(loadRunnable);
        PlayerManager.getInstance().getPlayingMusicLiveData().removeObserver(playingMusicObserver);
        rotateAnimation(false);
    }

    @Override
    public void rotateAnimation(boolean b) {
        if (rotateAnimation == null) {
            rotateAnimation = ValueAnimator.ofFloat(0f, 360f);
            rotateAnimation.setDuration(5000);
            rotateAnimation.setRepeatCount(-1);
            rotateAnimation.setInterpolator(new LinearInterpolator());
            rotateAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    Float value = (Float) animation.getAnimatedValue();
                    if (bottomBinding != null) {
                        bottomBinding.icon.setRotation(value);
                    }
                }
            });
        }
        if (rotateAnimation.isPaused() && b) {
            rotateAnimation.resume();
        } else if (b) {
            rotateAnimation.start();
        } else {
            rotateAnimation.pause();
        }
    }
}
