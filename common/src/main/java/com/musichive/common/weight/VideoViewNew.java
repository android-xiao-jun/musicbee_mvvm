package com.musichive.common.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.bumptech.glide.Glide;
import com.danikula.videocache.HttpProxyCacheServer;
import com.musichive.common.bean.nft.HomeNFTVideoBean;
import com.musichive.common.utils.ProxyVideoCacheManager;

import org.jetbrains.annotations.NotNull;

import xyz.doikki.videoplayer.player.VideoView;

/**
 * @Author Jun
 * Date 2021 年 06月 02 日 09:38
 * Description nft顶部视频播放view（主要解决触摸滚动问题）
 */
public class VideoViewNew extends VideoView implements DefaultLifecycleObserver {
    private HomeNFTVideoBean nftPicBean;
    private LifecycleOwner owner;

    public VideoViewNew(Context context) {
        super(context);
    }

    public VideoViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoViewNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void bindData(HomeNFTVideoBean bindVideoData, LifecycleOwner owner) {
        this.nftPicBean = bindVideoData;
        if (this.owner != null) {
            this.owner.getLifecycle().removeObserver(this);
        }
        this.owner = owner;
        owner.getLifecycle().addObserver(this);

        HttpProxyCacheServer cacheServer = ProxyVideoCacheManager.getProxy(getContext());
        String proxyUrl = cacheServer.getProxyUrl(nftPicBean.getUrl1());
        setUrl(proxyUrl);
        start();
        setMute(true);
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        resume();
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        pause();
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        release();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        release();
        if (this.owner != null) {
            this.owner.getLifecycle().removeObserver(this);
        }
        owner = null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        start();
        if (this.owner != null) {
            this.owner.getLifecycle().addObserver(this);
        }
    }
}
