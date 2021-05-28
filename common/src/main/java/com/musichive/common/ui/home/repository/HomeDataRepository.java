package com.musichive.common.ui.home.repository;

import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.data.response.ResponseStatus;
import com.musichive.common.api.HomeService;
import com.musichive.common.api.RetrofitApi;
import com.musichive.common.bean.ModelSubscriber;
import com.musichive.common.bean.home.HomeMusicDataBean;
import com.musichive.common.bean.home.ListBean;
import com.musichive.common.config.AppConfig;

import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 15:59
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeDataRepository {
    private static final HomeDataRepository S_REQUEST_MANAGER = new HomeDataRepository();
    private HomeService service1;

    private  HomeDataRepository() {
        service1 = RetrofitApi.getInstance().getService(AppConfig.NetWork.BASE_URL, HomeService.class);

    }

    public static HomeDataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }

    public void getHomeBanner(DataResult.Result<List<ListBean>> result) {
        RetrofitApi.addSubscribe(service1.musicSourceList(1, 20, 1, 1))
                .subscribe(new ModelSubscriber<List<ListBean>>() {

                    @Override
                    public void onSuccess(List<ListBean> data) {

                    }

                    @Override
                    public void onFailure(String errorCode) {

                    }

                    @Override
                    protected void resultMsg(String msg) {
                        super.resultMsg(msg);
                    }
                });
    }

    public void getHomeBannerMusicData(DataResult.Result<HomeMusicDataBean> result) {
        RetrofitApi.addSubscribe(service1.getMusicData())
                .subscribe(new ModelSubscriber<HomeMusicDataBean>() {

                    @Override
                    public void onSuccess(HomeMusicDataBean data) {
                        result.onResult(new DataResult<>(data, new ResponseStatus()));
                    }

                    @Override
                    public void onFailure(String errorCode) {
                        result.onResult(new DataResult<>(null, new ResponseStatus(errorCode, false)));

                    }
                });

    }

    public void obtainImageUrlPrefix() {
        RetrofitApi.addSubscribe(service1.obtainImageUrlPrefix())
                .subscribe(new ModelSubscriber<String>() {

                    @Override
                    public void onSuccess(String data) {
                        AppConfig.URLPREFIX = data;
                    }

                    @Override
                    public void onFailure(String errorCode) {
                    }
                });
    }

}
