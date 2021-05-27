package com.musichive.common.app;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.bumptech.glide.request.RequestOptions;
import com.musichive.base.glide.GlideUtils;
import com.musichive.common.R;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 15:35
 * Description 音乐蜜蜂-mvvm版本
 */
public class BaseApplication extends com.kunminx.architecture.BaseApplication {

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GlideUtils.init(new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
        );
    }
}
