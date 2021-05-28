package com.musichive.common.weight;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kunminx.architecture.ui.adapter.CommonViewPagerAdapter;
import com.musichive.common.R;
import com.musichive.common.config.AppConfig;
import com.musichive.common.databinding.CommonHomeBottomNavBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 17:38
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeBottomNav extends RelativeLayout {

    private CommonHomeBottomNavBinding bottomNavBinding;
    private List<BottomNavBtn> mBottomNavBtns;
    int[] icons = new int[]{
            R.drawable.home_tab_follow_selector,
            R.drawable.home_tab_nft_selector,
            R.drawable.home_tab_discover_selector,
            R.drawable.home_tab_creation_selector,
            R.drawable.home_tab_user_selector,
    };

    public HomeBottomNav(Context context) {
        this(context, null);
    }

    public HomeBottomNav(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomNav(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bottomNavBinding = CommonHomeBottomNavBinding.inflate(LayoutInflater.from(context), this, true);
        mBottomNavBtns = new ArrayList<>();

    }

    public void initTabAndPage() {
        mBottomNavBtns.clear();
        bottomNavBinding.homeNavLayout.removeAllViews();
        String[] title = getContext().getResources().getStringArray(R.array.bottomTabs);
        for (int i = 0; i < title.length; i++) {
            BottomNavBtn bottomNavBtn = genButtonNavBtn(i);
            bottomNavBtn.setTag(i);
            bottomNavBtn.setTextResId(title[i]);
            bottomNavBtn.setImageResource(icons[i]);
            if (i == AppConfig.HomeTab.HOME_BOTTOM_CENTER_INDEX) {
                bottomNavBtn.setVisibility(View.INVISIBLE);
            }
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            lp.gravity = Gravity.CENTER_VERTICAL;
            bottomNavBinding.homeNavLayout.addView(bottomNavBtn, lp);
            mBottomNavBtns.add(bottomNavBtn);
        }
        ViewPager viewPager = (getRootView()).findViewById(R.id.viewPager);
        if (viewPager != null) {
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    setBottomNavSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private BottomNavBtn genButtonNavBtn(final int index) {

        BottomNavBtn bottomNavBtn = new BottomNavBtn(getContext());
        bottomNavBtn.setTag(index);
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = (getRootView()).findViewById(R.id.viewPager);
                if (viewPager != null) {
                    viewPager.setCurrentItem(index);
                }
            }
        };
        if (index == AppConfig.HomeTab.HOME_BOTTOM_CENTER_INDEX) {
            bottomNavBinding.ivMarket.setOnClickListener(onClickListener);
            bottomNavBinding.tvMarketBg.setOnClickListener(onClickListener);
        }
        bottomNavBtn.setOnClickListener(onClickListener);
        return bottomNavBtn;
    }


    private void setBottomNavSelected(int position) {
        if (position == AppConfig.HomeTab.HOME_NFT_TAB_INDEX) {
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_TAB_INDEX).setImageResource(R.drawable.sy_fx_nft);
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_NFT_TAB_INDEX).setImageResource(R.drawable.sy_nft_nft);
            bottomNavBinding.ivMarket.setImageResource(R.drawable.sy_shiji);
            bottomNavBinding.tvMarketBg.setVisibility(View.VISIBLE);
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_WORKS_TAB_INDEX).setImageResource(R.drawable.sy_cz_nft);
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_ME_TAB_INDEX).setImageResource(R.drawable.sy_user_nft);

            mBottomNavBtns.get(AppConfig.HomeTab.HOME_TAB_INDEX).getmTextView().setTextColor(Color.parseColor("#9977C2FB"));
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_NFT_TAB_INDEX).getmTextView().setTextColor(Color.parseColor("#9977C2FB"));
            bottomNavBinding.tvText.setTextColor(Color.parseColor("#9977C2FB"));
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_WORKS_TAB_INDEX).getmTextView().setTextColor(Color.parseColor("#9977C2FB"));
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_ME_TAB_INDEX).getmTextView().setTextColor(Color.parseColor("#9977C2FB"));
        } else {

            mBottomNavBtns.get(AppConfig.HomeTab.HOME_TAB_INDEX).setImageResource(icons[AppConfig.HomeTab.HOME_TAB_INDEX]);
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_NFT_TAB_INDEX).setImageResource(icons[AppConfig.HomeTab.HOME_NFT_TAB_INDEX]);
            bottomNavBinding.ivMarket.setImageResource(R.drawable.sy_shiji3);
            bottomNavBinding.tvMarketBg.setVisibility(View.INVISIBLE);
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_WORKS_TAB_INDEX).setImageResource(icons[AppConfig.HomeTab.HOME_WORKS_TAB_INDEX]);
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_ME_TAB_INDEX).setImageResource(icons[AppConfig.HomeTab.HOME_ME_TAB_INDEX]);
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_TAB_INDEX).getmTextView().setTextColor(Color.parseColor("#1e1e1e"));
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_NFT_TAB_INDEX).getmTextView().setTextColor(Color.parseColor("#1e1e1e"));
            bottomNavBinding.tvText.setTextColor(Color.parseColor("#1e1e1e"));
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_WORKS_TAB_INDEX).getmTextView().setTextColor(Color.parseColor("#1e1e1e"));
            mBottomNavBtns.get(AppConfig.HomeTab.HOME_ME_TAB_INDEX).getmTextView().setTextColor(Color.parseColor("#1e1e1e"));
        }
        if (position == 1) {
            bottomNavBinding.homeNavLayout.setBackgroundResource(R.drawable.sy_nft_bottom_bg);
        } else {
            bottomNavBinding.homeNavLayout.setBackgroundResource(R.color.colorHomeBottomBg);
        }
        for (int i = 0; i < icons.length; i++) {
            mBottomNavBtns.get(i).setSelected(false);
        }
        mBottomNavBtns.get(position).setSelected(true);
        if (position == AppConfig.HomeTab.HOME_BOTTOM_CENTER_INDEX) {
            bottomNavBinding.ivMarket.setImageResource(R.drawable.sy_shiji2);
            bottomNavBinding.tvText.setTextColor(ContextCompat.getColor(getContext(), R.color.colormusicbee));
            return;
        }
        if (position != AppConfig.HomeTab.HOME_NFT_TAB_INDEX) {
            mBottomNavBtns.get(position).getmTextView().setTextColor(ContextCompat.getColor(getContext(), R.color.colormusicbee));
        }
    }

}
