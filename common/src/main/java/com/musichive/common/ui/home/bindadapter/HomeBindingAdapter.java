package com.musichive.common.ui.home.bindadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.musichive.base.glide.GlideUtils;
import com.musichive.base.wight.NoScrollViewPager;
import com.musichive.common.R;
import com.musichive.common.bean.home.ListBean;
import com.musichive.common.bean.music.TestAlbum;
import com.musichive.common.bean.nft.HomeNFTVideoBean;
import com.musichive.common.multi_adapter.BaseItemAdapter;
import com.musichive.common.other.BannerPlayerZoomPageTransformer;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.ui.home.weight.HomeTopView;
import com.musichive.common.ui.player.adapter.PlayerBannerAdapter;
import com.musichive.common.ui.player.weight.MarketTypeInfoView;
import com.musichive.common.utils.LogUtils;
import com.musichive.common.utils.ViewMapUtils;
import com.musichive.common.weight.AppMultiStateView;
import com.musichive.common.weight.AvatarImageTagView;
import com.musichive.common.weight.HomeBottomNav;
import com.musichive.common.weight.RoundCornerImageView;
import com.musichive.common.weight.VideoViewNew;
import com.musichive.common.weight.lyrics.LrcView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerAdapter;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;
import com.zhy.view.flowlayout.FlowLayout;

import java.util.List;

import xyz.doikki.videoplayer.player.VideoView;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 17:50
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeBindingAdapter {
    @BindingAdapter(value = {"setAdapter", "setOffscreenPageLimit"}, requireAll = false)
    public static void setAdapter(NoScrollViewPager viewPager, PagerAdapter adapter, int limit) {
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(limit);
    }

    @BindingAdapter(value = {"setAdapter"}, requireAll = false)
    public static void setAdapter(RecyclerView recyclerView, BaseItemAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"setViewPagerAdapter", "setOffscreenPageLimit"}, requireAll = false)
    public static void setViewPagerAdapter(ViewPager viewPager, PagerAdapter adapter, int limit) {
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(limit);
    }

    @BindingAdapter(value = {"setAdapter"}, requireAll = false)
    public static void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    @BindingAdapter(value = {"setLayoutManager"}, requireAll = false)
    public static void setLayoutManager(RecyclerView recyclerView,RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
    }

    @BindingAdapter(value = {"setViewWidth"}, requireAll = false)
    public static void setViewWidth(View view,int w) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width=w;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter(value = {"setMarginLeft"}, requireAll = false)
    public static void setMarginLeft(View view,int margindLeft) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        layoutParams.setMarginStart(margindLeft);
        view.setLayoutParams(layoutParams);
    }


    @BindingAdapter(value = {"initTabAndPage"}, requireAll = false)
    public static void initTabAndPage(HomeBottomNav homeBottomNav, boolean initTabAndPage) {
        homeBottomNav.initTabAndPage();
    }

    @BindingAdapter(value = {"initTop"}, requireAll = false)
    public static void initTop(HomeTopView homeTopView, boolean initTop) {
        homeTopView.initTop();
    }

    @BindingAdapter(value = {"resetY"}, requireAll = false)
    public static void resetY(HomeTopView homeTopView, boolean resetY) {
        homeTopView.resetY();
    }

    @BindingAdapter(value = {"loadPic"}, requireAll = false)
    public static void loadPic(AvatarImageTagView tagView, String pic) {
        tagView.loadPic(pic);
    }

    @BindingAdapter(value = {"loadPic"}, requireAll = false)
    public static void loadPic(RoundCornerImageView roundCornerImageView, Object pic) {
        GlideUtils.loadPicToImageViewAsBitmap(roundCornerImageView.getContext(), pic, roundCornerImageView);
    }

    @BindingAdapter(value = {"bindBannerData"}, requireAll = false)
    public static void bindBannerData(Banner banner, List<ListBean> listBannerBean) {
        banner.setAdapter(new BannerImageAdapter<ListBean>(listBannerBean) {
            @Override
            public void onBindView(BannerImageHolder holder, ListBean data, int position, int size) {
                //图片加载自己实现
                GlideUtils.loadPicToImageView(holder.imageView.getContext(), data.getCoverUrlLink(), holder.imageView);
            }
        });
    }

    @BindingAdapter(value = {"addBannerLifecycleObserver"}, requireAll = false)
    public static void addBannerLifecycleObserver(Banner banner, LifecycleOwner owner) {
        banner.addBannerLifecycleObserver(owner);
    }

    @BindingAdapter(value = {"loadPic"}, requireAll = false)
    public static void loadPic(ImageView imageView, Object pic) {
        GlideUtils.loadPicToImageView(imageView.getContext(), pic, imageView);
    }

    @BindingAdapter(value = {"bindVideoData", "bindLifecycleOwner"}, requireAll = false)
    public static void bindVideoData(VideoViewNew videoViewNew, HomeNFTVideoBean bindVideoData, LifecycleOwner owner) {
        videoViewNew.bindData(bindVideoData, owner);
    }

    @BindingAdapter(value = {"addOnStateChangeListener"}, requireAll = false)
    public static void addOnStateChangeListener(VideoViewNew videoViewNew, VideoView.OnStateChangeListener stateChangeListener) {
        videoViewNew.addOnStateChangeListener(stateChangeListener);
    }

    @BindingAdapter(value = {"bindDrawable"}, requireAll = false)
    public static void bindDrawable(ImageButton imageButton, int srcId) {
        imageButton.setImageResource(srcId);
    }

    @BindingAdapter(value = {"bindLrcData"}, requireAll = false)
    public static void bindLrcData(LrcView lrcView, String str) {
        lrcView.initLyricString(str);
    }

    @BindingAdapter(value = {"updateLyrics"}, requireAll = false)
    public static void updateLyrics(LrcView lrcView, int updateLyricsCurrentPosition) {
        lrcView.updateLyrics(updateLyricsCurrentPosition);
    }

    @BindingAdapter(value = {"bindBannerDataPlayer", "setOnBannerListener"}, requireAll = false)
    public static void bindBannerDataPlayer(Banner banner, PlayerBannerAdapter adapter, OnBannerListener listener) {
        LogUtils.e("bindBannerDataPlayer");
        banner.setAdapter(adapter);
        banner.setPageTransformer(new BannerPlayerZoomPageTransformer());
        banner.getViewPager2().setOffscreenPageLimit(adapter.datas.size());
        banner.setDatas(adapter.datas);
        banner.setOnBannerListener(listener);
        banner.setCurrentItem(PlayerManager.getInstance().getAlbumIndex() + 1, false);
    }
//
//    @BindingAdapter(value = {"setCurrentItem","smoothScroll"}, requireAll = false)
//    public static void setCurrentItem(Banner banner, int playIndex, boolean smoothScroll) {
//        LogUtils.e("setCurrentItem "+playIndex);
//        banner.setCurrentItem(playIndex+banner.getStartPosition(),smoothScroll);
//    }

    @BindingAdapter(value = {"addOnPageChangeListener"}, requireAll = false)
    public static void addOnPageChangeListener(Banner banner, OnPageChangeListener listener) {
        banner.addOnPageChangeListener(listener);
    }

    @BindingAdapter(value = {"addOnPageChangeListener"}, requireAll = false)
    public static void addOnPageChangeListener(ViewPager viewPager, ViewPager.OnPageChangeListener listener) {
        viewPager.addOnPageChangeListener(listener);
    }

    @BindingAdapter(value = {"setupWithViewPager"}, requireAll = false)
    public static void setupWithViewPager(XTabLayout xTabLayout, ViewPager viewPager) {
        xTabLayout.setupWithViewPager(viewPager);
    }

    @BindingAdapter(value = {"setOnCheckedChangeListener"}, requireAll = false)
    public static void setOnCheckedChangeListener(SwitchCompat switchCompat, CompoundButton.OnCheckedChangeListener listener) {
        switchCompat.setOnCheckedChangeListener(listener);
    }

    @BindingAdapter(value = {"time", "year"}, requireAll = false)
    public static void setMarketTypeInfoView(MarketTypeInfoView marketTypeInfoView, String time, String year) {
        marketTypeInfoView.setMarketInfo(time, year);
    }

    @BindingAdapter(value = {"bindMusicGenreName"}, requireAll = false)
    public static void bindMusicGenreName(FlowLayout flowLayout, String names) {
        flowLayout.removeAllViews();
        if (names != null && names.trim().length() > 0) {
            String[] genreNames = names.split(",");
            for (int i = 0; i < genreNames.length; i++) {
                View cacheView = ViewMapUtils.getCacheView(flowLayout.getContext(), genreNames[i]);
                TextView tv = cacheView.findViewById(R.id.player_music_type_tv);
                tv.setText(genreNames[i]);
                flowLayout.addView(cacheView);
            }
        }
    }

    @BindingAdapter(value = {"bindState"}, requireAll = false)
    public static void bindState(AppMultiStateView appMultiStateView, int state) {
        appMultiStateView.bindState(state);
    }
}
