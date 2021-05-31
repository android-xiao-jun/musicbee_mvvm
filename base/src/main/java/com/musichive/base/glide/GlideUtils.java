package com.musichive.base.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 09:05
 * Description glide
 */
public class GlideUtils {
    private static RequestOptions requestOptions = new RequestOptions();

    public static void init(RequestOptions r) {
        requestOptions = r;
    }

    public static void loadPicToImageView(Context context, Object url, ImageView imageView) {
        Glide.with(context).load(url).apply(requestOptions).into(imageView);
    }

    public static void loadPicToImageViewAsBitmap(Context context, Object url, ImageView imageView) {
        Glide.with(context).asBitmap().load(url).apply(requestOptions).into(imageView);
    }

    public static <T extends View> void loadPicToBackgroundView(Context context, String url, T view) {
        Glide.with(context).asDrawable().load(url).apply(requestOptions).into(new CustomTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                view.setBackground(resource);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                view.setBackground(placeholder);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                view.setBackground(errorDrawable);
            }
        });
    }
}
