package com.musichive.common.other.float_player;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kunminx.player.bean.dto.ChangeMusic;
import com.musichive.base.glide.GlideUtils;
import com.musichive.common.R;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.utils.HandlerUtils;
import com.musichive.common.weight.CircleImageView;

/**
 * @Author Jun
 * Date 2021年5月31日15:25:14
 * Description 悬浮播放view
 */
public class PlayerToolTwoView extends PlayerToolViewDataListener {

    private CircleImageView icon;
    private TextView tv_name;
    private ImageView iv_play;
    private ValueAnimator rotateAnimation;
    private View view_tempo_float;

    public PlayerToolTwoView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerToolTwoView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerToolTwoView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_layout_float_bottom, this);
        view_tempo_float = findViewById(R.id.view_tempo_float);
        icon = findViewById(R.id.icon);
        tv_name = findViewById(R.id.tv_name);
        iv_play = findViewById(R.id.iv_play);
        iv_play.setOnClickListener(v -> {
            HandlerUtils.getInstance().postWork(() -> {
                if ( PlayerManager.getInstance().isPlaying()){
                    PlayerManager.getInstance().pauseAudio();
                }else {
                    PlayerManager.getInstance().playAudio();
                }
            });
        });
        findViewById(R.id.iv_play_next).setOnClickListener(v -> {
            HandlerUtils.getInstance().postWork(() -> {
                PlayerManager.getInstance().playNext();
            });
        });
    }

    @Override
    public void upData(ChangeMusic changeMusic) {
        super.upData(changeMusic);
        tv_name.setText(changeMusic.getTitle());
    }

    @Override
    public void upPlayStatus(boolean isPlay) {
        super.upPlayStatus(isPlay);
        if (isPlay) {
            iv_play.setImageResource(R.drawable.ic_action_pause);
        } else {
            iv_play.setImageResource(R.drawable.ic_action_play);
        }
    }

    @Override
    public void initFpsLayout() {

    }

    @Override
    public void setFloatClick(OnClickListener listener) {
        view_tempo_float.setOnClickListener(listener);
    }

    @Override
    public void loadPic(Object pic) {
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
        if (icon != null && icon.getVisibility() != VISIBLE) {
            icon.setVisibility(VISIBLE);
        }
        GlideUtils.loadPicToImageViewAsBitmap(getContext(), pic, icon);
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
