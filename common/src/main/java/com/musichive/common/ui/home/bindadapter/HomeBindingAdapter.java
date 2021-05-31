package com.musichive.common.ui.home.bindadapter;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import com.musichive.base.glide.GlideUtils;
import com.musichive.base.wight.NoScrollViewPager;
import com.musichive.common.bean.home.ListBean;
import com.musichive.common.multi_adapter.BaseItemAdapter;
import com.musichive.common.ui.home.weight.HomeTopView;
import com.musichive.common.weight.AvatarImageTagView;
import com.musichive.common.weight.HomeBottomNav;
import com.musichive.common.weight.RoundCornerImageView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;

import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 17:50
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeBindingAdapter {
    @BindingAdapter(value = {"setAdapter"}, requireAll = true)
    public static void setAdapter(NoScrollViewPager viewPager, PagerAdapter adapter) {
        viewPager.setAdapter(adapter);
    }

    @BindingAdapter(value = {"setAdapter"}, requireAll = false)
    public static void setAdapter(RecyclerView recyclerView, BaseItemAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
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
}
