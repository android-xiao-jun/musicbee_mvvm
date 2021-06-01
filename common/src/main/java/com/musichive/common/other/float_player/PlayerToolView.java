package com.musichive.common.other.float_player;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.musichive.base.glide.GlideUtils;
import com.musichive.common.R;
import com.musichive.common.weight.CircleImageView;

/**
 * @Author Jun
 * Date 2021年5月31日15:25:14
 * Description 悬浮播放view
 */
public class PlayerToolView extends FrameLayout {

    private CircleImageView icon;
    private ValueAnimator rotateAnimation;
    private View view_tempo_float;

    public PlayerToolView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerToolView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerToolView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_layout_float_imp, this);
        view_tempo_float = findViewById(R.id.view_tempo_float);
        icon = findViewById(R.id.icon);
    }

    public void initFpsLayout() {

    }
    public void setFloatClick(View.OnClickListener listener){
        view_tempo_float.setOnClickListener(listener);
    }

    public void loadPic(Object pic) {
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
        if (icon != null && icon.getVisibility() != VISIBLE) {
            icon.setVisibility(VISIBLE);
        }
        GlideUtils.loadPicToImageViewAsBitmap(getContext(), pic, icon);
    }

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
                    if (icon != null) {
                        icon.setRotation(value);
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
