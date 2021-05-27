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

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.kunminx.architecture.ui.adapter.CommonViewPagerAdapter;
import com.musichive.common.R;
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

    public HomeBottomNav(Context context) {
        this(context, null);
    }

    public HomeBottomNav(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomNav(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bottomNavBinding = CommonHomeBottomNavBinding.inflate(LayoutInflater.from(context), this, true);
        mBottomNavBtns=new ArrayList<>();
    }

    public void initTabAndPage() {
        mBottomNavBtns.clear();
        bottomNavBinding.homeNavLayout.removeAllViews();
        String[] title = getContext().getResources().getStringArray(R.array.bottomTabs);
        int[] icons = getContext().getResources().getIntArray(R.array.bottomTabIcons);
        for (int i = 0; i < title.length; i++) {
            BottomNavBtn bottomNavBtn = genButtonNavBtn(i);
            bottomNavBtn.setTag(i);
            bottomNavBtn.setTextResId(title[i]);
            bottomNavBtn.setImageResource(icons[i]);
            if (i == 2) {
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
        bottomNavBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = (getRootView()).findViewById(R.id.viewPager);
                if (viewPager != null) {
                    viewPager.setCurrentItem(index);
                }
            }
        });
        return bottomNavBtn;
    }

//    private void setBottomNavSelected(final int prePos, final int curPos) {
//        if (curPos == HomeRefreshEvent.TAB_POSITION_NFT) {
//            mBottomNavBtns.get(0).setImageResource(R.drawable.sy_fx_nft);
//            mBottomNavBtns.get(1).setImageResource(R.drawable.sy_nft_nft);
//            iv_market.setImageResource(R.drawable.sy_shiji);
//            tv_market_bg.setVisibility(View.VISIBLE);
//            mBottomNavBtns.get(3).setImageResource(R.drawable.sy_cz_nft);
//            mBottomNavBtns.get(4).setImageResource(R.drawable.sy_user_nft);
//            mBottomNavBtns.get(0).getmTextView().setTextColor(Color.parseColor("#9977C2FB"));
//            mBottomNavBtns.get(1).getmTextView().setTextColor(Color.parseColor("#FF77C2FB"));
//            tv_text.setTextColor(Color.parseColor("#9977C2FB"));
//            mBottomNavBtns.get(3).getmTextView().setTextColor(Color.parseColor("#9977C2FB"));
//            mBottomNavBtns.get(4).getmTextView().setTextColor(Color.parseColor("#9977C2FB"));
//        } else {
//            mBottomNavBtns.get(0).setImageResource(mFragments.get(0).getNavIconResId());
//            mBottomNavBtns.get(1).setImageResource(mFragments.get(1).getNavIconResId());
//            iv_market.setImageResource(R.drawable.sy_shiji3);
//            tv_market_bg.setVisibility(View.INVISIBLE);
//            mBottomNavBtns.get(3).setImageResource(mFragments.get(3).getNavIconResId());
//            mBottomNavBtns.get(4).setImageResource(mFragments.get(4).getNavIconResId());
//            mBottomNavBtns.get(0).getmTextView().setTextColor(getResources().getColor(R.color.color_1e1e1e));
//            mBottomNavBtns.get(1).getmTextView().setTextColor(getResources().getColor(R.color.color_1e1e1e));
//            tv_text.setTextColor(getResources().getColor(R.color.color_1e1e1e));
//            mBottomNavBtns.get(3).getmTextView().setTextColor(getResources().getColor(R.color.color_1e1e1e));
//            mBottomNavBtns.get(4).getmTextView().setTextColor(getResources().getColor(R.color.color_1e1e1e));
//        }
//        if (prePos >= 0) {
//            mBottomNavBtns.get(prePos).setSelected(false);
//        }
//        if (curPos >= 0) {
//            mBottomNavBtns.get(curPos).setSelected(true);
//            if (curPos == 2) {
//                iv_market.setImageResource(R.drawable.sy_shiji2);
//                tv_text.setTextColor(getResources().getColor(R.color.colormusicbee));
//                return;
//            }
//            if (curPos != HomeRefreshEvent.TAB_POSITION_NFT) {
//                mBottomNavBtns.get(curPos).getmTextView().setTextColor(getResources().getColor(R.color.colormusicbee));
//            }
//        }
//    }
}
