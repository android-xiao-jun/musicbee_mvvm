package com.musichive.common.other.float_player;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kunminx.player.bean.dto.ChangeMusic;
import com.musichive.base.glide.GlideUtils;
import com.musichive.common.R;
import com.musichive.common.databinding.ViewLayoutFloatBottomBinding;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.utils.HandlerUtils;

/**
 * @Author Jun
 * Date 2021年5月31日15:25:14
 * Description 悬浮播放view
 */
public class PlayerToolTwoView extends PlayerToolViewDataListener {

    private ViewLayoutFloatBottomBinding bottomBinding;
    private ValueAnimator rotateAnimation;

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
            HandlerUtils.getInstance().postWork(() -> {
                if (PlayerManager.getInstance().isPlaying()) {
                    PlayerManager.getInstance().pauseAudio();
                } else {
                    PlayerManager.getInstance().playAudio();
                }
            });
        });
        bottomBinding.ivPlayNext.setOnClickListener(v -> {
            HandlerUtils.getInstance().postWork(() -> {
                PlayerManager.getInstance().playNext();
            });
        });
    }

    @Override
    public void upData(ChangeMusic changeMusic) {
        super.upData(changeMusic);
        bottomBinding.tvName.setText(changeMusic.getTitle());
    }

    @Override
    public void upPlayStatus(boolean isPlay) {
        super.upPlayStatus(isPlay);
        if (isPlay) {
            bottomBinding.ivPlay.setImageResource(R.drawable.ic_action_pause);
        } else {
            bottomBinding.ivPlay.setImageResource(R.drawable.ic_action_play);
        }
    }

    @Override
    public void initFpsLayout() {

    }

    @Override
    public void setFloatClick(OnClickListener listener) {
        bottomBinding.viewTempoFloat.setOnClickListener(listener);
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
