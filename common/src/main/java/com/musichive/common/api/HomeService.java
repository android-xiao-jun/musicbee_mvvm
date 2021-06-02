package com.musichive.common.api;

import com.musichive.common.bean.BaseResponseBean;
import com.musichive.common.bean.PageInfo;
import com.musichive.common.bean.home.HomeDynamicInfo;
import com.musichive.common.bean.home.HomeMusicDataBean;
import com.musichive.common.bean.home.ListBean;
import com.musichive.common.bean.nft.HomeNFTBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 16:07
 * Description 音乐蜜蜂-mvvm版本
 */
public interface HomeService {
    /**
     * 获取图片链接域名
     */
    @GET("api/config/getImageUrl")
    Observable<BaseResponseBean<String>> obtainImageUrlPrefix();

    /**
     * 首页Bannner
     */
    @GET("api/musicLive/getLiveBanner")
    Observable<BaseResponseBean<List<ListBean>>> musicSourceList(@Query("belongto") int belongto,
                                                                 @Query("limit") int limit,
                                                                 @Query("orderBy") int orderBy,
                                                                 @Query("page") int page
    );

    //获取首页存证作品和音乐人数量
    @POST("api/data/getMusicData")
    Observable<BaseResponseBean<HomeMusicDataBean>> getMusicData();

    //获取首页音乐人动态
    @GET("api/data/getHomePageDynamicInfo")
    Observable<BaseResponseBean<HomeDynamicInfo>> getHomePageDynamicInfo(@Query("page") int page, @Query("pageSize") int pageSize);

    //获取所有nft作品列表
    @GET("api/nft/getNftPostsAll")
    Observable<BaseResponseBean<PageInfo<HomeNFTBean>>> getNftPostsAll(@Query("page") int page, @Query("pageSize") int pageSize);
}
