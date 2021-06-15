package com.musichive.musicbee.api;

import com.musichive.main.bean.BaseResponseBean;
import com.musichive.musicbee.bean.AdvertisementBean;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author Jun
 * Date 2021 年 06月 10 日 16:46
 * Description 音乐蜜蜂-mvvm版本
 */
public interface AppService {

    /**
     * 根据类型获取广告列表
     */
    @POST("api/ad/{adType}")
    Observable<BaseResponseBean<AdvertisementBean>> getListByAdType(@Path("adType") String type, @Query("adType") String adType);
}
