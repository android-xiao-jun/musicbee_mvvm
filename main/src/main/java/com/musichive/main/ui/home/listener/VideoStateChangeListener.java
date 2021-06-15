package com.musichive.main.ui.home.listener;

import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ScreenUtils;
import com.musichive.main.ui.home.viewmodel.NFTFragmentViewModel;

import xyz.doikki.videoplayer.player.VideoView;

/**
 * @Author Jun
 * Date 2021 年 06月 02 日 10:13
 * Description 音乐蜜蜂-mvvm版本
 */
public class VideoStateChangeListener implements VideoView.OnStateChangeListener {

    private VideoView mVideoView;
    private View rl_video_bg;
    private View mThumb;
    private NFTFragmentViewModel nftFragmentViewModel;

    public VideoStateChangeListener(VideoView mVideoView, View rl_video_bg, View mThumb, NFTFragmentViewModel nftFragmentViewModel) {
        this.mVideoView = mVideoView;
        this.rl_video_bg = rl_video_bg;
        this.mThumb = mThumb;
        this.nftFragmentViewModel = nftFragmentViewModel;
    }

    @Override
    public void onPlayerStateChanged(int playerState) {

    }

    @Override
    public void onPlayStateChanged(int playState) {
        switch (playState) {
            case VideoView.STATE_PLAYING:
                int[] videoSize = mVideoView.getVideoSize();
                int w = videoSize[0];
                int h = videoSize[1];
                int screenWidth = ScreenUtils.getScreenWidth();
                float scale = (screenWidth * 1.f) / (w * 1.0f);
                ViewGroup.LayoutParams layoutParams = rl_video_bg.getLayoutParams();
                layoutParams.width = screenWidth;
                layoutParams.height = (int) (h * scale);
                rl_video_bg.setLayoutParams(layoutParams);

                ViewGroup.LayoutParams layoutParams1 = mThumb.getLayoutParams();
                layoutParams1.width = layoutParams.width;
                layoutParams1.height = layoutParams.height;
                mThumb.setLayoutParams(layoutParams1);

                nftFragmentViewModel.showVideoLoadPic.set(false);
                break;
            case VideoView.STATE_PAUSED:
            case VideoView.STATE_ERROR:
            case VideoView.STATE_BUFFERING:
            case VideoView.STATE_BUFFERED:
            case VideoView.STATE_PLAYBACK_COMPLETED:
                nftFragmentViewModel.showVideoLoadPic.set(false);
                break;
            case VideoView.STATE_PREPARING:
            case VideoView.STATE_IDLE:
            case VideoView.STATE_START_ABORT:
                nftFragmentViewModel.showVideoLoadPic.set(true);
                break;
            default:
                break;
        }
    }
}

