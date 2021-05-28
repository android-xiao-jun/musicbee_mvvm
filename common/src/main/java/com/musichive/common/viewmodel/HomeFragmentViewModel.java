package com.musichive.common.viewmodel;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.kunminx.architecture.data.response.DataResult;
import com.musichive.common.bean.home.HomeBannerBean;
import com.musichive.common.bean.home.HomeMusicDataBean;
import com.musichive.common.bean.home.ListBean;
import com.musichive.common.ui.home.repository.HomeDataRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:50
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeFragmentViewModel extends ViewModel {
    public final ObservableField<String> indexStr = new ObservableField<String>();

    public final ObservableBoolean initTop = new ObservableBoolean();
    public final ObservableBoolean autoRefresh = new ObservableBoolean();

    public final MutableLiveData<List<Object>> list = new MutableLiveData<>();

    {
        initTop.set(true);
        autoRefresh.set(true);
    }

    private HomeDataRepository homeDataRepository;

    public HomeFragmentViewModel(HomeDataRepository homeDataRepository) {
        this.homeDataRepository = homeDataRepository;
        homeList.setValue(new ArrayList<>());
    }

    //    List<Object> list = new ArrayList<>();
//        for (int i = 0; i < 20; i++) {
//        list.add(new TextBean("AAA" + i));
//        list.add(new ImageTextBean("img2", "BBB" + i));
//    }
    //首页顶部bean
    public MutableLiveData<HomeMusicDataBean> bannerModel = new MutableLiveData<>();

    public MutableLiveData<List<ListBean>> bannerList = new MutableLiveData<>();

    public MutableLiveData<List<Object>> homeList = new MutableLiveData<>();

    public void loadOne() {

    }

    public void requestLoadMore() {

    }

    public void requestRefresh() {
        homeDataRepository.getHomeBanner(new DataResult.Result<List<ListBean>>() {
            @Override
            public void onResult(DataResult<List<ListBean>> dataResult) {
                List<Object> value = homeList.getValue();
                HomeBannerBean homeBannerBean = new HomeBannerBean();
                for (Object o : value) {
                    if (o instanceof HomeBannerBean) {
                        value.set(0, homeBannerBean);
                        break;
                    }
                }
                value.add(0, homeBannerBean);
                homeList.postValue(value);
                bannerList.postValue(dataResult.getResult());
            }
        });
        homeDataRepository.getHomeBannerMusicData(new DataResult.Result<HomeMusicDataBean>() {
            @Override
            public void onResult(DataResult<HomeMusicDataBean> dataResult) {
                List<Object> value = homeList.getValue();
                for (Object o : value) {
                    if (o instanceof HomeBannerBean) {
                        value.set(0, dataResult.getResult());
                        break;
                    }
                }
                value.add(0, dataResult.getResult());
                homeList.postValue(value);
                bannerModel.postValue(dataResult.getResult());
            }
        });
    }
}
