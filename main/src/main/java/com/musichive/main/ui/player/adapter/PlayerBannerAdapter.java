package com.musichive.main.ui.player.adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.musichive.base.glide.GlideUtils;
import com.musichive.main.bean.music.TestAlbum;
import com.musichive.main.databinding.PlayerBannerBinding;
import com.musichive.main.player.PlayerManager;
import com.youth.banner.adapter.BannerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 02 日 17:39
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerBannerAdapter extends BannerAdapter<TestAlbum.TestMusic, PlayerBannerAdapter.BannerPlayerHolder> {

    private Context context;
    public List<TestAlbum.TestMusic> datas;

    public PlayerBannerAdapter(Context context, List<TestAlbum.TestMusic> datas) {
        super(datas);
        this.context = context;
        this.datas = datas;
    }

    @Override
    public BannerPlayerHolder onCreateHolder(ViewGroup parent, int viewType) {
        PlayerBannerBinding inflate = PlayerBannerBinding.inflate(LayoutInflater.from(context), parent, false);
        return new BannerPlayerHolder(inflate.getRoot(), inflate);
    }

    @Override
    public void onBindView(BannerPlayerHolder holder, TestAlbum.TestMusic data, int position, int size) {
        holder.bindView(data, position);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull @NotNull BannerPlayerHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.clear();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull @NotNull BannerPlayerHolder holder) {
        super.onViewAttachedToWindow(holder);
        holder.attached();
    }

    public void playAnim(boolean b) {
        BannerPlayerHolder viewHolder = getViewHolder();
        if (viewHolder != null) {
            viewHolder.rotateAnimation(b);
            notifyDataSetChanged();
        }
    }

    public class BannerPlayerHolder extends RecyclerView.ViewHolder {
        PlayerBannerBinding playerBannerBinding;
        private ValueAnimator rotateAnimation;

        public BannerPlayerHolder(@NonNull View view, PlayerBannerBinding playerBannerBinding) {
            super(view);
            this.playerBannerBinding = playerBannerBinding;
        }

        public void bindView(TestAlbum.TestMusic data, int position) {
            GlideUtils.loadPicToImageViewAsBitmap(context, data.getCoverImg(), playerBannerBinding.playerBigAvatar);
            GlideUtils.loadPicToImageViewAsBitmap(context, data.getCoverImg(), playerBannerBinding.playerSmallAvatar);
        }

        public void attached() {
            rotateAnimation(PlayerManager.getInstance().isPlaying());
        }

        public void clear() {
            if (rotateAnimation != null) {
                rotateAnimation.cancel();
                rotateAnimation = null;
            }
        }

        public void rotateAnimation(boolean b) {
            if (rotateAnimation == null && b) {
                float start = 0;
                if (playerBannerBinding != null) {
                    start = playerBannerBinding.rlBg.getRotation();
                }
                if (start >= 360f) {
                    start = start - 360f;
                }
                rotateAnimation = ValueAnimator.ofFloat(start, start + 360f);
                rotateAnimation.setDuration(7200);
                rotateAnimation.setRepeatCount(-1);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.addUpdateListener(animation -> {
                    Float value = (Float) animation.getAnimatedValue();
                    if (playerBannerBinding != null) {
                        playerBannerBinding.rlBg.setRotation(value);
                    }
                });
            }
            if (rotateAnimation == null) {
                return;
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


}
