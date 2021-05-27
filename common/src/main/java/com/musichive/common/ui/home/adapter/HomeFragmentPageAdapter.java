package com.musichive.common.ui.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;


/**
 * @Author Jun
 * Date 2021 年 05月 27 日 17:54
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeFragmentPageAdapter extends FragmentPagerAdapter {

    public List<Fragment> mList;

    public HomeFragmentPageAdapter(FragmentManager fm, final List<Fragment> mList) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mList = mList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

}
