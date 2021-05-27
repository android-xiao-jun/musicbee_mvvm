package com.musichive.base.glide;

import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 15:28
 * Description 音乐蜜蜂-mvvm版本
 */
public class BackgroundView<T extends View, Z> extends CustomViewTarget<T,Z> {

    public BackgroundView(T view) {
        super(view);
    }

    @Override
    protected void onResourceCleared(@Nullable  Drawable placeholder) {
        
    }

    @Override
    public void onLoadFailed(@Nullable  Drawable errorDrawable) {

    }

    @Override
    public void onResourceReady(@NonNull Object resource, @Nullable  Transition transition) {

    }
}
