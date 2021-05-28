package com.musichive.common.ui.home.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.app.BaseStatusBarFragment;
import com.musichive.common.bean.home.HomeBannerBean;
import com.musichive.common.bean.home.HomeEmptyBean;
import com.musichive.common.bean.home.HomeServiceBean;
import com.musichive.common.bean.home.MusicStateMusic;
import com.musichive.common.bean.home.MusicStateText;
import com.musichive.common.databinding.CommonNoDataBinding;
import com.musichive.common.databinding.HomeItemLayoutTypeServiceBinding;
import com.musichive.common.databinding.ItemMusicianStateMusicBinding;
import com.musichive.common.databinding.ItemMusicianStatePictextBinding;
import com.musichive.common.test.BaseItemAdapter;
import com.musichive.common.test.DataBindViewHolderManager;
import com.musichive.common.ui.home.repository.HomeDataRepository;
import com.musichive.common.utils.ToastUtils;
import com.musichive.common.viewmodel.HomeFragmentViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:48
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeFragment extends BaseStatusBarFragment {

    private HomeFragmentViewModel homeFragmentViewModel;
    private BaseItemAdapter homeAdapter;
    private HomeDataRepository homeDataRepository;

    @Override
    protected void initViewModel() {
        homeDataRepository = HomeDataRepository.getInstance();
        homeFragmentViewModel = getFragmentScopeViewModel(HomeFragmentViewModel.class, new FACTORY(homeDataRepository));
        homeFragmentViewModel.indexStr.set("home");
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        homeAdapter = new BaseItemAdapter();
        homeAdapter.register(HomeBannerBean.class, new DataBindViewHolderManager<>(R.layout.common_item_home_banner, BR.bannerData));
        homeAdapter.register(HomeServiceBean.class, new DataBindViewHolderManager<>(R.layout.home_item_layout_type_service,
                (DataBindViewHolderManager.ItemBindView<HomeServiceBean, HomeItemLayoutTypeServiceBinding>) (dataBinding, data) -> {
                    dataBinding.setClickProxy(new ClickProxy());
                }));
        homeAdapter.register(MusicStateText.class, new DataBindViewHolderManager<>(R.layout.item_musician_state_pictext,
                (DataBindViewHolderManager.ItemBindView<MusicStateText, ItemMusicianStatePictextBinding>) (dataBinding, data) -> {
                    dataBinding.setData(data);
                    dataBinding.setViewModel(homeFragmentViewModel);
                    dataBinding.setClickProxy(new ClickProxy());
                }));
        homeAdapter.register(MusicStateMusic.class, new DataBindViewHolderManager<>(R.layout.item_musician_state_music,
                (DataBindViewHolderManager.ItemBindView<MusicStateMusic, ItemMusicianStateMusicBinding>) (dataBinding, data) -> {
                    dataBinding.setData(data);
                    dataBinding.setViewModel(homeFragmentViewModel);
                    dataBinding.setClickProxy(new ClickProxy());
                }));
        homeAdapter.register(HomeEmptyBean.class, new DataBindViewHolderManager<>(R.layout.common_no_data,
                (DataBindViewHolderManager.ItemBindView<HomeEmptyBean, CommonNoDataBinding>) (dataBinding, data) -> {
                    dataBinding.setDataEmpty(data);
                    dataBinding.setViewModel(homeFragmentViewModel);
                })
        );
        return new DataBindingConfig(R.layout.common_fragment_home, BR.viewModel, homeFragmentViewModel)
                .addBindingParam(BR.adapter, homeAdapter);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeFragmentViewModel.homeList.observe(getViewLifecycleOwner(), o -> {
            homeAdapter.setDataItems(o);
            homeAdapter.notifyDataSetChanged();
        });
    }

    public class RefreshEvent implements OnRefreshLoadMoreListener {

        @Override
        public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
            homeFragmentViewModel.requestLoadMore();
        }

        @Override
        public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
            homeFragmentViewModel.requestRefresh();
        }
    }

    public class ClickProxy {
        public void homeServiceClick(int index) {
            ToastUtils.showShort("开发中" + index);
        }

        public void homeDynamicClick(int index) {
            ToastUtils.showShort("动态开发中" + index);
        }
    }

    public static class FACTORY implements ViewModelProvider.Factory {

        public FACTORY(HomeDataRepository homeDataRepository) {
            this.homeDataRepository = homeDataRepository;
        }

        private HomeDataRepository homeDataRepository;

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            HomeFragmentViewModel homeFragmentViewModel = new HomeFragmentViewModel(homeDataRepository);
            return (T) homeFragmentViewModel;

        }
    }
}
