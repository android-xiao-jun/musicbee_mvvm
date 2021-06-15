package com.musichive.common.ui.home.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.gyf.immersionbar.ImmersionBar;
import com.kunminx.architecture.data.response.manager.NetworkStateManager;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.aop.SingleClick;
import com.musichive.common.app.BaseStatusBarFragment;
import com.musichive.common.bean.home.HomeBannerModel;
import com.musichive.common.bean.home.HomeDynamicInfo;
import com.musichive.common.bean.home.HomeEmptyBean;
import com.musichive.common.bean.home.HomeServiceBean;
import com.musichive.common.bean.home.MusicStateMusic;
import com.musichive.common.bean.home.MusicStateText;
import com.musichive.common.databinding.CommonItemHomeBannerBinding;
import com.musichive.common.databinding.CommonNoDataBinding;
import com.musichive.common.databinding.HomeItemLayoutTypeServiceBinding;
import com.musichive.common.databinding.ItemMusicianStateMusicBinding;
import com.musichive.common.databinding.ItemMusicianStatePictextBinding;
import com.musichive.common.multi_adapter.BaseItemAdapter;
import com.musichive.common.multi_adapter.DataBindViewHolderManager;
import com.musichive.common.player.helper.PlayerDataTransformUtils;
import com.musichive.common.ui.home.repository.FACTORY;
import com.musichive.common.ui.home.repository.HomeDataRepository;
import com.musichive.common.ui.home.weight.HomeTopView;
import com.musichive.common.utils.HandlerUtils;
import com.musichive.common.utils.ToastUtils;
import com.musichive.common.ui.home.viewmodel.HomeFragmentViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:48
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeFragment extends BaseStatusBarFragment {

    private HomeFragmentViewModel homeFragmentViewModel;
    private BaseItemAdapter homeAdapter;
    private HomeDataRepository homeDataRepository;
    private TopStatusListener topStatusListener;

    @Override
    protected void initViewModel() {
        homeDataRepository = HomeDataRepository.getInstance();
        homeFragmentViewModel = getFragmentScopeViewModel(HomeFragmentViewModel.class, new FACTORY(homeDataRepository));
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        homeAdapter = new BaseItemAdapter() {
            @Override
            public int getPageDefault() {
                return 0;
            }
        };
        topStatusListener = new TopStatusListener(this);
        homeAdapter.register(HomeBannerModel.class, new DataBindViewHolderManager<>(R.layout.common_item_home_banner,
                (DataBindViewHolderManager.ItemBindView<HomeBannerModel, CommonItemHomeBannerBinding>) (dataBinding, data, position) -> {
                    dataBinding.setBannerData(data);
                    dataBinding.setClickProxy(new ClickProxy());
                    dataBinding.setOwner(getViewLifecycleOwner());
                }));
        homeAdapter.register(HomeServiceBean.class, new DataBindViewHolderManager<>(R.layout.home_item_layout_type_service,
                (DataBindViewHolderManager.ItemBindView<HomeServiceBean, HomeItemLayoutTypeServiceBinding>) (dataBinding, data, position) -> {
                    dataBinding.setClickProxy(new ClickProxy());
                }));
        homeAdapter.register(MusicStateText.class, new DataBindViewHolderManager<>(R.layout.item_musician_state_pictext,
                (DataBindViewHolderManager.ItemBindView<MusicStateText, ItemMusicianStatePictextBinding>) (dataBinding, data, position) -> {
                    dataBinding.setData(data);
                    dataBinding.setClickProxy(new ClickProxy());
                }));
        homeAdapter.register(MusicStateMusic.class, new DataBindViewHolderManager<>(R.layout.item_musician_state_music,
                (DataBindViewHolderManager.ItemBindView<MusicStateMusic, ItemMusicianStateMusicBinding>) (dataBinding, data, position) -> {
                    dataBinding.setData(data);
                    dataBinding.setClickProxy(new ClickProxy());
                }));
        homeAdapter.register(HomeEmptyBean.class, new DataBindViewHolderManager<>(R.layout.common_no_data,
                (DataBindViewHolderManager.ItemBindView<HomeEmptyBean, CommonNoDataBinding>) (dataBinding, data, position) -> {
                    dataBinding.setDataEmpty(data);
                    dataBinding.setClick(new ClickProxy());
                })
        );
        return new DataBindingConfig(R.layout.common_fragment_home, BR.viewModel, homeFragmentViewModel)
                .addBindingParam(BR.adapter, homeAdapter)
                .addBindingParam(BR.refreshEvent, new RefreshEvent())
                .addBindingParam(BR.topListener, topStatusListener);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        homeFragmentViewModel.homeList.observe(getViewLifecycleOwner(), o -> {
            if (o != null) {
                homeAdapter.setDataItems(o);
                homeAdapter.notifyDataSetChanged();
            }
            homeFragmentViewModel.closeLoad.set(true);
            homeFragmentViewModel.closeRefresh.set(true);
            homeFragmentViewModel.closeLoad.notifyChange();
            homeFragmentViewModel.closeRefresh.notifyChange();
        });
        homeDataRepository.urlPrefixLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null && homeAdapter != null) {
                    homeAdapter.notifyDataSetChanged();
                    homeDataRepository.urlPrefixLiveData.removeObserver(this);
                }
            }
        });
        homeFragmentViewModel.requestRefresh(homeAdapter.getPage(), homeAdapter.getPageSize());
        NetworkStateManager.getInstance().getNetWorkStateLiveData().observe(this, aBoolean -> {
            if (aBoolean && (homeAdapter.getDataList() != null && homeAdapter.getDataList().size() <= 3)) {
                homeAdapter.setPage(homeAdapter.getPageDefault());
                homeFragmentViewModel.requestRefresh(homeAdapter.getPage(), homeAdapter.getPageSize());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public class RefreshEvent implements OnRefreshLoadMoreListener {

        @Override
        public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
            homeAdapter.setPage(homeAdapter.getPage() + 1);
            homeFragmentViewModel.requestLoadMore(homeAdapter.getPage(), homeAdapter.getPageSize());
        }

        @Override
        public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
            homeFragmentViewModel.resetY.set(true);
            homeFragmentViewModel.resetY.notifyChange();
            homeAdapter.setPage(homeAdapter.getPageDefault());
            homeFragmentViewModel.requestRefresh(homeAdapter.getPage(), homeAdapter.getPageSize());
        }
    }

    public class ClickProxy {
        public ClickProxy() {
        }

        public void homeServiceClick(int index) {
            ToastUtils.showShort("开发中" + index);
        }

        public void homeDynamicClick(String id) {
            ToastUtils.showShort("动态开发中" + id);
        }

        public void homeEmptyClick() {
            homeFragmentViewModel.requestRefresh(homeAdapter.getPage(), homeAdapter.getPageSize());
        }

        //活动点击
        public void homeActivityClick() {
            ToastUtils.showShort("活动点击");
        }

        @SingleClick
        public void homeDynamicClickMusic(HomeDynamicInfo.ListBean listBean) {
            HandlerUtils.getInstance().postWork(() -> {
                PlayerDataTransformUtils.homeMusicAddPlayList(homeAdapter.getDataList(), listBean);
            });
        }
    }

    public static class TopStatusListener implements HomeTopView.HomeBannerTopStatusBgListener {
        public boolean old = false;
        private Fragment fragment;

        public TopStatusListener(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public void onStatus(boolean b) {
            if (old == b) {
                return;
            }
            old = b;
            ImmersionBar.with(fragment).statusBarDarkFont(b).init();
        }

        public void clear() {
            fragment = null;
            old = false;
        }
    }

    @Override
    public View getTitleBar() {
        if (getView() == null) {
            return null;
        }
        return getView().findViewById(R.id.home_top).findViewById(R.id.cl_bg);
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }


    @Override
    public boolean statusBarDarkFont() {
        return topStatusListener.old;
    }

    @Override
    public void onDestroyView() {
        if (topStatusListener != null) {
            topStatusListener.clear();
        }
        super.onDestroyView();
    }
}
