package com.musichive.main.ui.home.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.kunminx.architecture.data.response.manager.NetworkStateManager;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.main.BR;
import com.musichive.main.R;
import com.musichive.main.app.BaseStatusBarFragment;
import com.musichive.main.bean.home.UserLabe;
import com.musichive.main.ui.home.adapter.HomeFragmentPageAdapter;
import com.musichive.main.ui.home.adapter.MusicLibraryTypeAdapter;
import com.musichive.main.ui.home.viewmodel.MusicLibFragmentViewModel;
import com.musichive.main.utils.XTablayoutUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author Jun
 * Date 2021 年 06月 08 日 13:33
 * Description 音乐蜜蜂-mvvm版本
 */
public class MusicLibFragment extends BaseStatusBarFragment implements CompoundButton.OnCheckedChangeListener {
    private MusicLibFragmentViewModel libFragmentViewModel;
    private MusicLibraryTypeAdapter typeAdapter;
    private MusicLibraryTypeAdapter styleAdapter;
    public List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();

    private HomeFragmentPageAdapter homeFragmentPageAdapter;

    @Override
    protected void initViewModel() {
        libFragmentViewModel = getActivityScopeViewModel(MusicLibFragmentViewModel.class);
        homeFragmentPageAdapter = new HomeFragmentPageAdapter(getChildFragmentManager(), fragments) {
            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        };
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        typeAdapter = new MusicLibraryTypeAdapter(getContext(), libFragmentViewModel.getTypeList());
        styleAdapter = new MusicLibraryTypeAdapter(getContext(), libFragmentViewModel.getStyleList());

        typeAdapter.setItemClickListener((viewHolder, position, data) -> {
            libFragmentViewModel.getTypeData().postValue(data.getId());
        });
        styleAdapter.setItemClickListener((viewHolder, position, data) -> {
            libFragmentViewModel.getStyleData().postValue(data.getId());
        });
        return new DataBindingConfig(R.layout.common_fragment_music_lib, BR.viewModel, libFragmentViewModel)
                .addBindingParam(BR.adapterType, typeAdapter)
                .addBindingParam(BR.adapterStyle, styleAdapter)
                .addBindingParam(BR.switchChangeListener, this)
                .addBindingParam(BR.viewpagerAdapter, homeFragmentPageAdapter)
                .addBindingParam(BR.layoutManagerType, new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false))
                .addBindingParam(BR.layoutManagerStyle, new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        libFragmentViewModel.getTypeLiveData().observe(this, userLabes -> {
            typeAdapter.notifyDataSetChanged();
        });

        libFragmentViewModel.getStyleLiveData().observe(this, userLabes -> {
            styleAdapter.notifyDataSetChanged();
        });
        libFragmentViewModel.getHeatLiveData().observe(this, userLabes -> {
            initTabLayout(userLabes);
        });
        libFragmentViewModel.getTypeSwitchLiveData().observe(this, integer -> {
            libFragmentViewModel.typeSwitch.set(integer);
        });
        libFragmentViewModel.loadTypeAndStyle();

        NetworkStateManager.getInstance().getNetWorkStateLiveData().observe(this, aBoolean -> {
            if (aBoolean && fragments.isEmpty()) {
                libFragmentViewModel.loadTypeAndStyle();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initTabLayout(List<UserLabe> userLabes) {
        if (userLabes == null || userLabes.isEmpty()) {
            return;
        }
        fragments.clear();
        titles.clear();
        for (int i = 0; i < userLabes.size(); i++) {
            UserLabe userLabe = userLabes.get(i);
            titles.add(userLabe.getTitle());
            NewestNewFragment fragment = new NewestNewFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", libFragmentViewModel.getTypeList().get(0).getId());
            bundle.putInt("style", libFragmentViewModel.getStyleList().get(0).getId());
            bundle.putInt("ordertype", userLabe.getId());
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        homeFragmentPageAdapter.notifyDataSetChanged();
        getBinding().setVariable(BR.viewpagerChangeListener, onPageChangeListener);
        getBinding().setVariable(BR.tabLayoutBindViewPager, getBinding().getRoot().findViewById(R.id.groups_post_vp));
        getBinding().getRoot().findViewById(R.id.groups_post_vp).post(() -> {
            setViewSize(0);
        });
    }

    public ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setViewSize(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void setViewSize(int position) {
        int[] datas = XTablayoutUtils.setViewSize((XTabLayout) getBinding().getRoot().findViewById(R.id.groups_post_tablayout), position);
        libFragmentViewModel.tabWidth.set(datas[0]);
        libFragmentViewModel.tabLeftMargin.set(datas[1]);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean b) {
        if (b) {
            libFragmentViewModel.checkTextColor.set(Color.parseColor("#ff1e1e1e"));
            libFragmentViewModel.getTypeSwitchLiveData().postValue(1);
        } else {
            libFragmentViewModel.checkTextColor.set(Color.parseColor("#B5B5B5"));
            libFragmentViewModel.getTypeSwitchLiveData().postValue(0);
        }
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    public View getTitleBar() {
        return getView().findViewById(R.id.ll_title);
    }
}
