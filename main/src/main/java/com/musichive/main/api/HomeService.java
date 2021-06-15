package com.musichive.main.api;

import com.musichive.main.bean.BaseResponseBean;
import com.musichive.main.bean.PageInfo;
import com.musichive.main.bean.home.HomeDynamicInfo;
import com.musichive.main.bean.home.HomeMusicDataBean;
import com.musichive.main.bean.home.ListBean;
import com.musichive.main.bean.home.MusicLabelResponseBean;
import com.musichive.main.bean.home.MusicLibBean;
import com.musichive.main.bean.nft.HomeNFTBean;

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


    @POST("api/musicLabel/musicLabelList")
    Observable<MusicLabelResponseBean> getMusicLabel(@Query("page") String page, @Query("rows") int rows);

    @GET("api/search/post-by-tile")
    Observable<BaseResponseBean<MusicLibBean>> postByTitle(@Query("colligate") boolean colligate,
                                                           @Query("conf_type_id") int conf_type_id,
                                                           @Query("genreList") String genreList,
                                                           @Query("isShare") int isShare,
                                                           @Query("orderType") int orderType,
                                                           @Query("page") int page,
                                                           @Query("pageSize") int pageSize);
}
