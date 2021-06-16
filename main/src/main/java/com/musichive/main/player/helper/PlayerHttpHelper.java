package com.musichive.main.player.helper;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.kunminx.architecture.data.response.DataResult;
import com.musichive.main.app.BaseApplication;
import com.musichive.main.bean.music.GoodsPlayerBean;
import com.musichive.main.bean.music.MusicLibPlayerBean;
import com.musichive.main.bean.music.NFTPlayerBean;
import com.musichive.main.bean.music.TestAlbum;
import com.musichive.main.player.PlayerManager;
import com.musichive.main.room.MusicDatabase;
import com.musichive.main.room.MusicEntity;
import com.musichive.main.utils.LogUtils;

/**
 * @Author Jun
 * Date 2021 年 06月 04 日 17:27
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerHttpHelper {

    /**
     * 修改里面判断 记得修改 PlayerHttpHelper 中 getPlayDataLiveData 这个方法
     *
     * @param listener
     */
    public static void playRequest(UpViewDataListener listener) {
        TestAlbum.TestMusic playingMusic = PlayerManager.getInstance().getCurrentPlayingMusic();
        if (playingMusic == null) {
            if (listener != null) {
                listener.upError("");
            }
            return;
        }

        //先取之前 保存的旧值
        Object oldData = getPlayDataLiveData();
        if (oldData != null) {
            PlayerManager.getInstance().getPlayDataLiveData().postValue(oldData);
            if (listener != null) {
                listener.upData();
            }
        }

        int type = playingMusic.getType();//0 乐库 1 市集 2 nft播放
        if (type == 0) {
            obtainDiscoverHotspot(playingMusic.getArtist().getAccount(), playingMusic.getMusicId(), listener);
        } else if (type == 1) {
            getMusicPlayByGoodsId(playingMusic.getMusicId(), listener);
        } else if (type == 2) {
            getMusicPlayInfo(playingMusic.getMusicId(), listener);
        } else {
            if (listener != null) {
                listener.upError("类型不对");
            }
        }
    }

    public static void getMusicPlayByGoodsId(String goodsId, UpViewDataListener listener) {
        PlayDataRepository playDataRepository = PlayDataRepository.getInstance();
        playDataRepository.getMusicPlayByGoodsId(goodsId, new DataResult.Result<Object>() {
            @Override
            public void onResult(DataResult<Object> dataResult) {
                //子线程
                if (goodsId.equals(resultCall(dataResult))) {
                    //如果当前网络请求回来，这首歌还在播放，则更新这首歌数据
                    if (listener != null) {
                        listener.upData();
                    }
                } else {
                    if (listener != null) {
                        listener.upError("");
                    }
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
                if (nftId.equals(resultCall(dataResult))) {
                    //如果当前网络请求回来，这首歌还在播放，则更新这首歌数据
                    if (listener != null) {
                        listener.upData();
                    }
                } else {
                    if (listener != null) {
                        listener.upError("");
                    }
                }
            }
        });
    }

    public static void obtainDiscoverHotspot(String author, String permlink, UpViewDataListener listener) {
        PlayDataRepository playDataRepository = PlayDataRepository.getInstance();
        playDataRepository.obtainDiscoverHotspot(author, permlink, dataResult -> {
            if (permlink.equals(resultCall(dataResult))) {
                //如果当前网络请求回来，这首歌还在播放，则更新这首歌数据
                if (listener != null) {
                    listener.upData();
                }
            } else {
                if (listener != null) {
                    listener.upError("");
                }
            }
        });
    }

    public static String resultCall(DataResult<Object> dataResult) {
        //子线程
        Object result = dataResult.getResult();
        LogUtils.e("网络访问结束--" + result);
        PlayerManager.getInstance().getPlayDataLiveData().postValue(result);
        if (result != null) {
            TestAlbum.TestMusic playingMusic = PlayerManager.getInstance().getCurrentPlayingMusic();
            if (playingMusic == null) {
                return "";
            }
            String musicId = playingMusic.getMusicId();
            playingMusic.setExpand(JSON.toJSONString(result));
            MusicEntity musicEntity = PlayerDataTransformUtils.transformHomeMusic(playingMusic);
            MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().updateMusic(musicEntity);

            return musicId;
        }
        return "";
    }

    /**
     * 修改里面判断 记得修改 PlayerHttpHelper 中 playRequest 这个方法
     */
    public static Object getPlayDataLiveData() {
        TestAlbum.TestMusic playingMusic = PlayerManager.getInstance().getCurrentPlayingMusic();
        if (playingMusic == null || TextUtils.isEmpty(playingMusic.getExpand())) {
            return null;
        }
        int type = playingMusic.getType();//0 乐库 1 市集 2 nft播放
        if (type == 0) {
            return JSON.parseObject(playingMusic.getExpand(), MusicLibPlayerBean.class);
        } else if (type == 1) {
            return JSON.parseObject(playingMusic.getExpand(), GoodsPlayerBean.class);
        } else if (type == 2) {
            return JSON.parseObject(playingMusic.getExpand(), NFTPlayerBean.class);
        }
        return null;
    }

    public interface UpViewDataListener {
        void upData();

        default void upError(String msg) {

        }
    }
}
