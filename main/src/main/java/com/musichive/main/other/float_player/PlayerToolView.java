package com.musichive.main.other.float_player;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.musichive.base.glide.GlideUtils;
import com.musichive.main.databinding.ViewLayoutFloatImpBinding;

/**
 * @Author Jun
 * Date 2021年5月31日15:25:14
 * Description 悬浮播放view
 */
public class PlayerToolView extends PlayerToolViewDataListener {

    private ValueAnimator rotateAnimation;
    private ViewLayoutFloatImpBinding impBinding;

    public PlayerToolView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerToolView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerToolView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        impBinding = ViewLayoutFloatImpBinding.inflate(LayoutInflater.from(context), this, true);
    }

    @Override
    public void initFpsLayout() {

    }

    @Override
    public void setFloatClick(View.OnClickListener listener) {
        impBinding.viewTempoFloat.setOnClickListener(listener);
    }

    @Override
    public void loadPic(Object pic) {
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
        if (impBinding != null && impBinding.icon.getVisibility() != VISIBLE) {
            impBinding.icon.setVisibility(VISIBLE);
        }
        GlideUtils.loadPicToImageViewAsBitmap(getContext(), pic, impBinding.icon);
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
                    if (impBinding != null) {
                        impBinding.icon.setRotation(value);
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
