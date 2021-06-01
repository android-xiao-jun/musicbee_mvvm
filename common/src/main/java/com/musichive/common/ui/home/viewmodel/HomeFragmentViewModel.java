package com.musichive.common.ui.home.viewmodel;

import androidx.databinding.Observable;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.kunminx.architecture.data.response.DataResult;
import com.musichive.common.app.BaseViewModel;
import com.musichive.common.bean.home.HomeBannerModel;
import com.musichive.common.bean.home.HomeDynamicInfo;
import com.musichive.common.bean.home.HomeEmptyBean;
import com.musichive.common.bean.home.HomeMusicDataBean;
import com.musichive.common.bean.home.HomeServiceBean;
import com.musichive.common.bean.home.ListBean;
import com.musichive.common.bean.home.MusicStateMusic;
import com.musichive.common.bean.home.MusicStateText;
import com.musichive.common.ui.home.repository.HomeDataRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:50
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeFragmentViewModel extends BaseViewModel {

    public final ObservableBoolean initTop = new ObservableBoolean();
    public final ObservableBoolean resetY = new ObservableBoolean();
    public final ObservableBoolean autoRefresh = new ObservableBoolean();

    public final ObservableBoolean closeRefresh = new ObservableBoolean();

    public final ObservableBoolean closeLoad = new ObservableBoolean();

    public final ObservableBoolean notMoreList = new ObservableBoolean();

    {
        initTop.set(true);
    }

    private HomeDataRepository homeDataRepository;

    public HomeFragmentViewModel(HomeDataRepository homeDataRepository) {
        this.homeDataRepository = homeDataRepository;
        HomeBannerModel value = new HomeBannerModel();
        value.initActivityBean();
        bannerModelMutableLiveData.setValue(value);
        homeServiceBeanMutableLiveData.setValue(new HomeServiceBean());
        List<Object> objects = new ArrayList<>();
        objects.add(value);
        homeList.setValue(objects);
    }

    public MutableLiveData<List<Object>> homeList = new MutableLiveData<>();

    private MutableLiveData<HomeBannerModel> bannerModelMutableLiveData = new MutableLiveData<>();//首页顶部bean

    private MutableLiveData<HomeServiceBean> homeServiceBeanMutableLiveData = new MutableLiveData<>();//首页顶部bean

    public HomeServiceBean getHomeServiceModel() {
        return homeServiceBeanMutableLiveData.getValue();
    }

    public HomeBannerModel getHomeBannerModel() {
        return bannerModelMutableLiveData.getValue();
    }

    public List<Object> getHomeList() {
        return homeList.getValue();
    }

    public void requestLoadMore(int page, int pageSize) {
        homeDataRepository.getHomePageDynamicInfo(page, pageSize, dataResult -> {
            List<Object> value1 = getHomeList();
            HomeDynamicInfo result = dataResult.getResult();
            if (result != null && result.list != null&&!result.list.isEmpty()) {
                for (HomeDynamicInfo.ListBean listBean : result.list) {
                    if (listBean.type == 3 || listBean.type == 5) {
                        value1.add(new MusicStateMusic(listBean));
                    } else {
                        value1.add(new MusicStateText(listBean));
                    }
                }
                notMoreList.set(false);
            }else {
                notMoreList.set(true);
            }
            notMoreList.notifyChange();
            if (value1.size() == 2) {
                value1.add(new HomeEmptyBean());
            }
            homeList.postValue(value1);
        });
    }

    public void requestRefresh(int page, int pageSize) {
        List<Object> value = getHomeList();
        value.clear();
        value.add(getHomeBannerModel());
        value.add(getHomeServiceModel());
        homeList.setValue(value);

        homeDataRepository.getHomeBanner(dataResult -> {
            HomeBannerModel homeBannerModel = getHomeBannerModel();
            homeBannerModel.setListBannerBean(dataResult.getResult());
            bannerModelMutableLiveData.postValue(homeBannerModel);

            List<Object> value1 = getHomeList();
            value1.set(0, homeBannerModel);
            homeList.postValue(value1);
        });
        homeDataRepository.getHomeBannerMusicData(dataResult -> {

            HomeBannerModel homeBannerModel = getHomeBannerModel();
            homeBannerModel.setHomeMusicDataBean(dataResult.getResult());
            bannerModelMutableLiveData.postValue(homeBannerModel);
            List<Object> value12 = getHomeList();
            value12.set(0, homeBannerModel);
            homeList.postValue(value12);
        });
        requestLoadMore(page, pageSize);
    }


}
