package com.musichive.common.ui.home.fragment;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.kunminx.architecture.ui.page.LazyFragment;
import com.musichive.common.BR;
import com.musichive.common.R;
import com.musichive.common.ui.home.adapter.NewestNewAdapter;
import com.musichive.common.ui.home.repository.HomeDataRepository;
import com.musichive.common.ui.home.viewmodel.MusicLibFragmentChildViewModel;
import com.musichive.common.ui.home.viewmodel.MusicLibFragmentViewModel;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;


/**
 * @Author Jun
 * Date 2021 年 06月 10 日 10:04
 * Description 音乐蜜蜂-mvvm版本
 */
public class NewestNewFragment extends LazyFragment {
    private MusicLibFragmentViewModel libFragmentViewModelActivity;
    private MusicLibFragmentChildViewModel libFragmentViewModel;
    private NewestNewAdapter newestNewAdapter;

    @Override
    protected void initViewModel() {
        libFragmentViewModelActivity = getActivityScopeViewModel(MusicLibFragmentViewModel.class);
        libFragmentViewModel = getFragmentScopeViewModel(MusicLibFragmentChildViewModel.class);
        if (getArguments() != null) {
            libFragmentViewModel.typeData.set(getArguments().getInt("type", 0));
            libFragmentViewModel.styleData.set(getArguments().getInt("style", 0));
            libFragmentViewModel.ordertypeData.set(getArguments().getInt("ordertype", 0));
        }
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        newestNewAdapter = new NewestNewAdapter(getContext(), libFragmentViewModel.getListList());
        return new DataBindingConfig(R.layout.fragment_newest_new, BR.viewModel, libFragmentViewModel)
                .addBindingParam(BR.adapter, newestNewAdapter)
                .addBindingParam(BR.layoutManager, new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL))
                .addBindingParam(BR.refreshEvent, new RefreshEvent());
    }


    @Override
    public void lazyLoadData() {
        super.lazyLoadData();
        libFragmentViewModel.getListData().observe(getViewLifecycleOwner(), o -> {
            newestNewAdapter.notifyDataSetChanged();
            libFragmentViewModel.closeLoad.set(!libFragmentViewModel.closeLoad.get());
            libFragmentViewModel.closeRefresh.set(!libFragmentViewModel.closeRefresh.get());
        });
        libFragmentViewModelActivity.getTypeSwitchLiveData().observe(this, integer -> {
            libFragmentViewModel.typeSwitch.set(integer);
            newestNewAdapter.setPage(NewestNewAdapter.pageDefault);
            libFragmentViewModel.postByTitle(newestNewAdapter.getPage());
        });
        libFragmentViewModelActivity.getStyleData().observe(this, style -> {
            libFragmentViewModel.styleData.set(style);
            newestNewAdapter.setPage(NewestNewAdapter.pageDefault);
            libFragmentViewModel.postByTitle(newestNewAdapter.getPage());
        });
        libFragmentViewModelActivity.getTypeData().observe(this, type -> {
            libFragmentViewModel.typeData.set(type);
            newestNewAdapter.setPage(NewestNewAdapter.pageDefault);
            libFragmentViewModel.postByTitle(newestNewAdapter.getPage());
        });
        HomeDataRepository.getInstance().urlPrefixLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null && newestNewAdapter != null) {
                    newestNewAdapter.notifyDataSetChanged();
                    HomeDataRepository.getInstance().urlPrefixLiveData.removeObserver(this);
                }
            }
        });
        libFragmentViewModel.postByTitle(newestNewAdapter.getPage());
    }

    public class RefreshEvent implements OnRefreshLoadMoreListener {

        @Override
        public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
            newestNewAdapter.setPage(newestNewAdapter.getPage() + 1);
            libFragmentViewModel.postByTitle(newestNewAdapter.getPage());
        }

        @Override
        public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
            newestNewAdapter.setPage(NewestNewAdapter.pageDefault);
            libFragmentViewModel.postByTitle(newestNewAdapter.getPage());
        }
    }
}
