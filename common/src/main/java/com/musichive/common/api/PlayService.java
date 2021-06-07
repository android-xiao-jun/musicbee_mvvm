package com.musichive.common.api;

import com.musichive.common.bean.BaseResponseBean;
import com.musichive.common.bean.music.GoodsPlayerBean;
import com.musichive.common.bean.music.MusicLibPlayerBean;
import com.musichive.common.bean.music.NFTPlayerBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Author Jun
 * Date 2021 年 06月 04 日 17:31
 * Description 音乐蜜蜂-mvvm版本
 */
public interface PlayService {
    //根据商品id获取播放器播放所需数据
    @GET("api/seller/getMusicPlayByGoodsId")
    Observable<BaseResponseBean<GoodsPlayerBean>> getMusicPlayByGoodsId(@Query("goodsId") String goodsId);

//    //根据music_id获取播放器播放所需数据==出售中播放
//    @GET("api/seller/getOnSellDemoByMusicId")
//    Observable<BaseResponseBean<Shop2>> getOnSellDemoByMusicId(@Query("musicId") int musicId);


    @GET("api/posts/{author}/{permlink}")
    @Headers("api-version: 0")
    Observable<BaseResponseBean<MusicLibPlayerBean>> obtainDiscoverHotspot(@Path("author") String author
            , @Path("permlink") String permlink
            , @Query("version") int version);

    //获取播放器所需数据
    @GET("api/nft/getMusicPlayInfo")
    Observable<BaseResponseBean<NFTPlayerBean>> getMusicPlayInfo(@Query("nftPostsId") String nftPostsId);
}
