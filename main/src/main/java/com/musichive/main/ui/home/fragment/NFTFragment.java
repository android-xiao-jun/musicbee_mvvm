package com.musichive.main.ui.home.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.kunminx.architecture.data.response.manager.NetworkStateManager;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.base.glide.GlideUtils;
import com.musichive.main.BR;
import com.musichive.main.R;
import com.musichive.aop.SingleClick;
import com.musichive.main.app.BaseStatusBarFragment;
import com.musichive.main.bean.nft.HomeNFTBean;
import com.musichive.main.bean.nft.HomeNFTVideoBean;
import com.musichive.main.databinding.ItemNftListBinding;
import com.musichive.main.databinding.NftFragmentHeaderBinding;
import com.musichive.main.multi_adapter.BaseItemAdapter;
import com.musichive.main.multi_adapter.DataBindViewHolderManager;
import com.musichive.main.player.helper.PlayerDataTransformUtils;
import com.musichive.main.ui.home.listener.VideoStateChangeListener;
import com.musichive.main.ui.home.repository.FACTORY;
import com.musichive.main.ui.home.repository.HomeDataRepository;
import com.musichive.main.ui.home.viewmodel.NFTFragmentViewModel;
import com.musichive.main.utils.HandlerUtils;
import com.musichive.main.utils.ToastUtils;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:48
 * Description 音乐蜜蜂-mvvm版本
 */
public class NFTFragment extends BaseStatusBarFragment {

    private NFTFragmentViewModel nftFragmentViewModel;
    private BaseItemAdapter nftAdapter;

    @Override
    protected void initViewModel() {
        nftFragmentViewModel = getFragmentScopeViewModel(NFTFragmentViewModel.class, new FACTORY(HomeDataRepository.getInstance()));
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        nftAdapter = new BaseItemAdapter() {
            @Override
            public int getPageDefault() {
                return 0;
            }
        };
        nftAdapter.register(HomeNFTVideoBean.class, new DataBindViewHolderManager<>(R.layout.nft_fragment_header,
                (DataBindViewHolderManager.ItemBindView<HomeNFTVideoBean, NftFragmentHeaderBinding>) (dataBinding, data, position) -> {
                    dataBinding.setVideoBean(data);
                    dataBinding.setStateChangeListener(new VideoStateChangeListener(dataBinding.player, dataBinding.rlVideoBg, dataBinding.thumb, nftFragmentViewModel));
                    dataBinding.setLifecycleOwner(getViewLifecycleOwner());
                    dataBinding.setViewModel(nftFragmentViewModel);

                    //动态修改申请位置
                    ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) dataBinding.ivApply.getLayoutParams();
                    layoutParams.setMargins(ScreenUtils.getScreenWidth() / 2, 0, 0, 0);
                    dataBinding.ivApply.setLayoutParams(layoutParams);
                }));
        nftAdapter.register(HomeNFTBean.class, new DataBindViewHolderManager<>(R.layout.item_nft_list,
                (DataBindViewHolderManager.ItemBindView<HomeNFTBean, ItemNftListBinding>) (dataBinding, data, position) -> {
                    dataBinding.setItem(data);
                    dataBinding.setItemDouble(position % 2 == 0);
                    dataBinding.setClickProxy(new ClickProxy());
                }));
        return new DataBindingConfig(R.layout.common_fragment_nft, BR.viewModel, nftFragmentViewModel)
                .addBindingParam(BR.refreshEvent, new RefreshEvent())
                .addBindingParam(BR.adapter, nftAdapter)
                .addBindingParam(BR.clickProxy, new ClickProxy())
                .addBindingParam(BR.itemDecoration, itemDecoration);
    }

    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        nftFragmentViewModel.requestRefresh(nftAdapter.getPage(), nftAdapter.getPageSize());

        NetworkStateManager.getInstance().getNetWorkStateLiveData().observe(this, aBoolean -> {
            if (aBoolean && (nftAdapter.getDataList() != null && nftAdapter.getDataList().size() <= 1)) {
                nftAdapter.setPage(nftAdapter.getPageDefault());
                nftFragmentViewModel.requestRefresh(nftAdapter.getPage(), nftAdapter.getPageSize());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GlideUtils.preload(getContext(), nftFragmentViewModel.homeNFTVideoBean.nftPic);//预加载 视频占位图
        nftFragmentViewModel.nftList.observe(getViewLifecycleOwner(), o -> {
            if (o != null) {
                nftAdapter.setDataItems(o);
                nftAdapter.notifyDataSetChanged();
            }
            nftFragmentViewModel.closeLoad.set(true);
            nftFragmentViewModel.closeRefresh.set(true);
            nftFragmentViewModel.closeLoad.notifyChange();
            nftFragmentViewModel.closeRefresh.notifyChange();
        });
    }

    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    protected boolean statusBarDarkFont() {
        return false;
    }

    @Override
    public View getTitleBar() {
        if (getView() == null) {
            return null;
        }
        return getView().findViewById(R.id.view_bg);
    }

    private RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int layoutPosition = parent.getChildLayoutPosition(view);
            if (layoutPosition == 1) {
                outRect.top = -SizeUtils.dp2px(60);
            } else {
                outRect.top = 0;
            }
            if (nftFragmentViewModel.nftList.getValue() != null && layoutPosition == nftFragmentViewModel.nftList.getValue().size()) {
                outRect.bottom = SizeUtils.dp2px(60);
            } else {
                outRect.bottom = 0;
            }
        }
    };

    public class RefreshEvent implements OnRefreshLoadMoreListener {

        @Override
        public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
            nftAdapter.setPage(nftAdapter.getPage() + 1);
            nftFragmentViewModel.requestLoadMore(nftAdapter.getPage(), nftAdapter.getPageSize());
        }

        @Override
        public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
            nftFragmentViewModel.resetY.set(true);
            nftFragmentViewModel.resetY.notifyChange();
            nftAdapter.setPage(nftAdapter.getPageDefault());
            nftFragmentViewModel.requestRefresh(nftAdapter.getPage(), nftAdapter.getPageSize());
        }
    }

    public class ClickProxy {
        @SingleClick
        public void nftClickMusic(HomeNFTBean listBean) {
            HandlerUtils.getInstance().postWork(() -> {
                PlayerDataTransformUtils.nftMusicAddPlayList(nftFragmentViewModel.getNFTList(), listBean);
            });
        }

        @SingleClick
        public void nftClickMore() {
            ToastUtils.showShort("更多");
        }
    }
}
