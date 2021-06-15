package com.musichive.main.ui.home.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;

import com.musichive.main.app.BaseViewModel;
import com.musichive.main.bean.nft.HomeNFTBean;
import com.musichive.main.bean.nft.HomeNFTVideoBean;
import com.musichive.main.ui.home.repository.HomeDataRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 31 日 09:55
 * Description 音乐蜜蜂-mvvm版本
 */
public class NFTFragmentViewModel extends BaseViewModel {

    public final ObservableBoolean resetY = new ObservableBoolean();
    public final ObservableBoolean autoRefresh = new ObservableBoolean();

    public final ObservableBoolean closeRefresh = new ObservableBoolean();

    public final ObservableBoolean closeLoad = new ObservableBoolean();

    public final ObservableBoolean notMoreList = new ObservableBoolean();

    public final ObservableBoolean showVideoLoadPic = new ObservableBoolean();

    public MutableLiveData<List<Object>> nftList = new MutableLiveData<>();

    public HomeNFTVideoBean homeNFTVideoBean = new HomeNFTVideoBean();

    private HomeDataRepository homeDataRepository;

    public NFTFragmentViewModel(HomeDataRepository homeDataRepository) {
        this.homeDataRepository = homeDataRepository;
        List<Object> objects = new ArrayList<>();
        objects.add(homeNFTVideoBean);
        nftList.setValue(objects);
        showVideoLoadPic.set(true);
    }

    public List<Object> getNFTList() {
        return nftList.getValue();
    }

    public void requestLoadMore(int page, int pageSize) {
        homeDataRepository.getNftPostsAll(page, pageSize, dataResult -> {
            List<Object> value1 = getNFTList();
            List<HomeNFTBean> result = dataResult.getResult();
            if (result != null && !result.isEmpty()) {
                value1.addAll(result);
                notMoreList.set(false);
            } else {
                notMoreList.set(true);
            }
            notMoreList.notifyChange();
            nftList.postValue(value1);
        });
    }

    public void requestRefresh(int page, int pageSize) {
        List<Object> value = getNFTList();
        value.clear();
        value.add(homeNFTVideoBean);
        nftList.setValue(value);
        requestLoadMore(page, pageSize);
    }
}
