package com.musichive.common.player.helper;

import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.data.response.ResponseStatus;
import com.musichive.common.api.HomeService;
import com.musichive.common.api.PlayService;
import com.musichive.common.api.RetrofitApi;
import com.musichive.common.bean.ModelSubscriber;
import com.musichive.common.bean.music.GoodsPlayerBean;
import com.musichive.common.bean.music.MusicLibPlayerBean;
import com.musichive.common.bean.music.NFTPlayerBean;
import com.musichive.common.config.AppConfig;
import com.musichive.common.ui.home.repository.HomeDataRepository;


/**
 * @Author Jun
 * Date 2021 年 06月 04 日 17:29
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayDataRepository {
    private static final PlayDataRepository S_REQUEST_MANAGER = new PlayDataRepository();

    private PlayService service1;
    private PlayService service2;

    private PlayDataRepository() {
        service1 = RetrofitApi.getInstance().getService(AppConfig.NetWork.BASE_URL, PlayService.class);
        service2 = RetrofitApi.getInstance().getService(AppConfig.NetWork.BASE_URL2, PlayService.class);

    }

    public static PlayDataRepository getInstance() {
        return S_REQUEST_MANAGER;
    }


    public void obtainDiscoverHotspot(String author, String permlink, DataResult.Result<Object> result) {
        RetrofitApi.addSubscribe(service1.obtainDiscoverHotspot(author, permlink, 2))
                .subscribe(new ModelSubscriber<MusicLibPlayerBean>() {

                    @Override
                    public void onSuccess(MusicLibPlayerBean data) {
                        result.onResult(new DataResult<>(data, new ResponseStatus()));
                    }

                    @Override
                    public void onFailure(String errorCode) {
                        result.onResult(new DataResult<>(null, new ResponseStatus(errorCode, false)));
                    }

                    @Override
                    protected void resultMsg(String msg) {
                        super.resultMsg(msg);
                    }
                });
    }

    public void getMusicPlayByGoodsId(String goodsId, DataResult.Result<Object> result) {
        RetrofitApi.addSubscribe(service2.getMusicPlayByGoodsId(goodsId))
                .subscribe(new ModelSubscriber<GoodsPlayerBean>() {

                    @Override
                    public void onSuccess(GoodsPlayerBean data) {
                        result.onResult(new DataResult<>(data, new ResponseStatus()));
                    }

                    @Override
                    public void onFailure(String errorCode) {
                        result.onResult(new DataResult<>(null, new ResponseStatus(errorCode, false)));
                    }

                    @Override
                    protected void resultMsg(String msg) {
                        super.resultMsg(msg);
                    }
                });
    }

    public void getMusicPlayInfo(String nftId, DataResult.Result<Object> result) {
        RetrofitApi.addSubscribe(service1.getMusicPlayInfo(nftId))
                .subscribe(new ModelSubscriber<NFTPlayerBean>() {

                    @Override
                    public void onSuccess(NFTPlayerBean data) {
                        result.onResult(new DataResult<>(data, new ResponseStatus()));
                    }

                    @Override
                    public void onFailure(String errorCode) {
                        result.onResult(new DataResult<>(null, new ResponseStatus(errorCode, false)));
                    }

                    @Override
                    protected void resultMsg(String msg) {
                        super.resultMsg(msg);
                    }
                });
    }
}
