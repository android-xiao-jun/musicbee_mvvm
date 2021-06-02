package com.musichive.common.player.helper;

import com.musichive.common.app.BaseApplication;
import com.musichive.common.bean.home.HomeDynamicInfo;
import com.musichive.common.bean.home.MusicStateMusic;
import com.musichive.common.bean.music.TestAlbum;
import com.musichive.common.bean.nft.HomeNFTBean;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.room.MusicDatabase;
import com.musichive.common.room.MusicEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 09:35
 * Description 播放音频数据转换model
 */
public class PlayerDataTransformUtils {

    /**
     * 首页列表点击播放 并保存到数据库
     *
     * @param list
     * @param listBean
     */
    public static void homeMusicAddPlayList(List<Object> list, HomeDynamicInfo.ListBean listBean) {
        if (list == null) {
            return;
        }
        List<HomeDynamicInfo.ListBean> musicStateMusic = new ArrayList<>();
        for (Object o : list) {
            if (o instanceof MusicStateMusic) {
                MusicStateMusic item = (MusicStateMusic) o;
                musicStateMusic.add(item.item);
            }
        }
        int position = musicStateMusic.indexOf(listBean);
        if (position < 0) {
            position = 0;
        }
        PlayerDataTransformUtils.transformHomeMusic(musicStateMusic, position);
        List<MusicEntity> musicEntities = PlayerDataTransformUtils.transformHomeMusicToEntity(musicStateMusic);
        MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().deleteMusicAll();
        MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().insertMusics(musicEntities);
    }

    /**
     * 首页转换成播放器播放类型
     *
     * @param list
     * @param playIndex
     * @return
     */
    public static TestAlbum transformHomeMusic(List<HomeDynamicInfo.ListBean> list, int playIndex) {
        List<TestAlbum.TestMusic> musics = new ArrayList<>();
        for (HomeDynamicInfo.ListBean item : list) {
            TestAlbum.TestArtist testArtist = new TestAlbum.TestArtist();
            testArtist.setName(item.name);
            TestAlbum.TestMusic testMusic = new TestAlbum.TestMusic(item.demoInfoVO.goodsId, item.getCoverLink(), item.demoInfoVO.musicEncodeUrl, item.getDemoName(), testArtist);
            testMusic.setType(1);
            musics.add(testMusic);
        }
        TestAlbum testAlbum = new TestAlbum("", "", "", null, null, musics);
        PlayerManager.getInstance().loadAlbum(testAlbum, playIndex);
        return testAlbum;
    }

    /**
     * 首页动态列表转换成数据库 保存类型
     *
     * @param list
     * @return
     */
    public static List<MusicEntity> transformHomeMusicToEntity(List<HomeDynamicInfo.ListBean> list) {
        List<MusicEntity> musicEntities = new ArrayList<>();
        if (list != null) {
            for (HomeDynamicInfo.ListBean item : list) {
                MusicEntity musicEntity = new MusicEntity();
                musicEntity.musicId = item.demoInfoVO.goodsId;
                musicEntity.url = item.demoInfoVO.musicEncodeUrl;
                musicEntity.coverImg = item.getCoverLink();
                musicEntity.title = item.getDemoName();
                musicEntity.name = item.name;
                musicEntity.type = 1;
                musicEntities.add(musicEntity);
            }
        }
        return musicEntities;
    }

    /**
     * 从数据库实体类读取到播放列表
     *
     * @param list
     * @return
     */
    public static TestAlbum transformHomeMusic(List<MusicEntity> list) {
        List<TestAlbum.TestMusic> musics = new ArrayList<>();
        for (MusicEntity item : list) {
            TestAlbum.TestArtist testArtist = new TestAlbum.TestArtist();
            testArtist.setName(item.name);
            TestAlbum.TestMusic testMusic = new TestAlbum.TestMusic(item.musicId, item.coverImg, item.url, item.title, testArtist);
            testMusic.setType(item.type);
            musics.add(testMusic);
        }
        TestAlbum testAlbum = new TestAlbum("", "", "", null, null, musics);
        PlayerManager.getInstance().loadAlbum(testAlbum);
        return testAlbum;
    }


    /**
     * nft播放并保存到数据库
     *
     * @param list
     * @param listBean
     */
    public static void nftMusicAddPlayList(List<Object> list, HomeNFTBean listBean) {
        if (list == null) {
            return;
        }
        List<HomeNFTBean> musicStateMusic = new ArrayList<>();
        for (Object o : list) {
            if (o instanceof HomeNFTBean) {
                musicStateMusic.add((HomeNFTBean) o);
            }
        }
        int position = musicStateMusic.indexOf(listBean);
        if (position < 0) {
            position = 0;
        }
        PlayerDataTransformUtils.transformNftMusic(musicStateMusic, position);
        List<MusicEntity> musicEntities = PlayerDataTransformUtils.transformNftMusicToEntity(musicStateMusic);
        MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().deleteMusicAll();
        MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().insertMusics(musicEntities);
    }

    /**
     * nft 列表 类型转换成 数据库保存类型
     *
     * @param list
     * @return
     */
    public static List<MusicEntity> transformNftMusicToEntity(List<HomeNFTBean> list) {
        List<MusicEntity> musicEntities = new ArrayList<>();
        if (list != null) {
            for (HomeNFTBean item : list) {
                MusicEntity musicEntity = new MusicEntity();
                musicEntity.musicId = String.valueOf(item.nftPostsId);
                musicEntity.url = item.externalUrl;
                musicEntity.coverImg = item.getCoverLink();
                musicEntity.title = item.getNftName();
                musicEntity.name = item.getCreaterName();
                musicEntity.type = 2;
                musicEntities.add(musicEntity);
            }
        }
        return musicEntities;
    }

    /**
     * nft播放类型转换播放器播放类型
     *
     * @param list
     * @param playIndex
     * @return
     */
    public static TestAlbum transformNftMusic(List<HomeNFTBean> list, int playIndex) {
        List<TestAlbum.TestMusic> musics = new ArrayList<>();
        for (HomeNFTBean item : list) {
            TestAlbum.TestArtist testArtist = new TestAlbum.TestArtist();
            testArtist.setName(item.getCreaterName());
            TestAlbum.TestMusic testMusic = new TestAlbum.TestMusic(String.valueOf(item.nftPostsId), item.getCoverLink(), item.musicEncodeUrl, item.getNftName(), testArtist);
            testMusic.setType(2);
            musics.add(testMusic);
        }
        TestAlbum testAlbum = new TestAlbum("", "", "", null, null, musics);
        PlayerManager.getInstance().loadAlbum(testAlbum, playIndex);
        return testAlbum;
    }
}
