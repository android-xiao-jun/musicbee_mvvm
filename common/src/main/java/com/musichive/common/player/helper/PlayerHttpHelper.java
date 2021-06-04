package com.musichive.common.player.helper;

import com.kunminx.architecture.data.response.DataResult;
import com.musichive.common.app.BaseApplication;
import com.musichive.common.bean.music.TestAlbum;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.room.MusicDatabase;
import com.musichive.common.room.MusicEntity;
import com.musichive.common.utils.LogUtils;

/**
 * @Author Jun
 * Date 2021 年 06月 04 日 17:27
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerHttpHelper {

    public static void playRequest(UpViewDataListener listener) {
        TestAlbum.TestMusic playingMusic = PlayerManager.getInstance().getCurrentPlayingMusic();
        int type = playingMusic.getType();//0 乐库 1 市集 2 nft播放
        if (type == 0) {
            obtainDiscoverHotspot(playingMusic.getArtist().getAccount(), playingMusic.getMusicId(), listener);
        } else if (type == 1) {
            getMusicPlayByGoodsId(playingMusic.getMusicId(), listener);
        } else if (type == 2) {
            getMusicPlayInfo(playingMusic.getMusicId(), listener);
        } else {
            listener.upError("类型不对");
        }
    }

    public static void getMusicPlayByGoodsId(String goodsId, UpViewDataListener listener) {
        PlayDataRepository playDataRepository = PlayDataRepository.getInstance();
        playDataRepository.getMusicPlayByGoodsId(goodsId, new DataResult.Result<Object>() {
            @Override
            public void onResult(DataResult<Object> dataResult) {
                //子线程
                LogUtils.e(dataResult);
                TestAlbum.TestMusic playingMusic = PlayerManager.getInstance().getCurrentPlayingMusic();
                String musicId = playingMusic.getMusicId();

                MusicEntity musicEntity = PlayerDataTransformUtils.transformHomeMusic(playingMusic);
                MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().updateMusic(musicEntity);
                if (goodsId.equals(musicId)) {
                    //如果当前网络请求回来，这首歌还在播放，则更新这首歌数据
                    listener.upData();
                }
            }
        });
    }

    public static void getMusicPlayInfo(String nftId, UpViewDataListener listener) {
        PlayDataRepository playDataRepository = PlayDataRepository.getInstance();
        playDataRepository.getMusicPlayInfo(nftId, new DataResult.Result<Object>() {
            @Override
            public void onResult(DataResult<Object> dataResult) {
                //子线程
                LogUtils.e(dataResult);
                listener.upData();
            }
        });
    }

    public static void obtainDiscoverHotspot(String author, String permlink, UpViewDataListener listener) {
        PlayDataRepository playDataRepository = PlayDataRepository.getInstance();
        playDataRepository.obtainDiscoverHotspot(author, permlink, new DataResult.Result<Object>() {
            @Override
            public void onResult(DataResult<Object> dataResult) {
                //子线程
                LogUtils.e(dataResult);
                listener.upData();
            }
        });
    }

    public interface UpViewDataListener {
        void upData();

        default void upError(String msg) {

        }
    }
}
