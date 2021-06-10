package com.musichive.common.ui.home.repository;

import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.data.response.ResponseStatus;
import com.musichive.common.api.HomeService;
import com.musichive.common.api.RetrofitApi;
import com.musichive.common.bean.ModelSubscriber;
import com.musichive.common.bean.home.MusicLabelResponseBean;
import com.musichive.common.bean.home.MusicLibBean;
import com.musichive.common.config.AppConfig;

import java.util.List;


/**
 * @Author Jun
 * Date 2021 年 06月 10 日 09:49
 * Description 音乐蜜蜂-mvvm版本
 */
public class MusicDataRepository {
    public static final int PAGE_SIZE = 21;

    public static void getMusicLabel(DataResult.Result<MusicLabelResponseBean> result) {
        RetrofitApi.addSubscribe(RetrofitApi.getInstance()
                .getService(AppConfig.NetWork.BASE_URL, HomeService.class)
                .getMusicLabel("20", 1))
                .subscribe(musicLabelResponseBean->{
                    result.onResult(new DataResult<>(musicLabelResponseBean, new ResponseStatus()));
                },throwable -> {
                    result.onResult(new DataResult<>(null, new ResponseStatus("-1", false)));
                });
    }

    public static void postByTitle(int type,int style,int isShare,int ordertype,int page,DataResult.Result<MusicLibBean> result) {
        RetrofitApi.addSubscribe(RetrofitApi.getInstance()
                .getService(AppConfig.NetWork.BASE_URL, HomeService.class)
                .postByTitle(false, type, "" + style, isShare, ordertype, page, PAGE_SIZE))
                .subscribe(new ModelSubscriber<MusicLibBean>() {
                    @Override
                    public void onSuccess(MusicLibBean data) {
                        result.onResult(new DataResult<>(data, new ResponseStatus()));
                    }

                    @Override
                    public void onFailure(String errorCode) {
                        result.onResult(new DataResult<>(null, new ResponseStatus(errorCode, false)));
                    }
                });
    }
}
