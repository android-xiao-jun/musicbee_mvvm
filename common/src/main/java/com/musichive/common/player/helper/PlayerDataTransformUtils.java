package com.musichive.common.player.helper;

import com.musichive.common.app.BaseApplication;
import com.musichive.common.bean.home.HomeDynamicInfo;
import com.musichive.common.bean.home.MusicStateMusic;
import com.musichive.common.bean.music.TestAlbum;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.room.MusicDatabase;
import com.musichive.common.room.MusicEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 09:35
 * Description 播放音频数据转换model
 */
public class PlayerDataTransformUtils {

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


    public static TestAlbum transformHomeMusic(HomeDynamicInfo.ListBean item) {
        return transformHomeMusic(Collections.singletonList(item), 0);
    }

    public static TestAlbum transformHomeMusic(List<HomeDynamicInfo.ListBean> list, int playIndex) {
        List<TestAlbum.TestMusic> musics = new ArrayList<>();
        for (HomeDynamicInfo.ListBean item : list) {
            TestAlbum.TestArtist testArtist = new TestAlbum.TestArtist();
            testArtist.setName(item.name);
            TestAlbum.TestMusic testMusic = new TestAlbum.TestMusic(item.demoInfoVO.goodsId, item.getCoverLink(), item.demoInfoVO.musicEncodeUrl, item.getDemoName(), testArtist);
            musics.add(testMusic);
        }
        TestAlbum testAlbum = new TestAlbum("", "", "", null, null, musics);
        PlayerManager.getInstance().loadAlbum(testAlbum, playIndex);
        return testAlbum;
    }

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
                musicEntities.add(musicEntity);
            }
        }
        return musicEntities;
    }


    public static TestAlbum transformHomeMusic(List<MusicEntity> list) {
        List<TestAlbum.TestMusic> musics = new ArrayList<>();
        for (MusicEntity item : list) {
            TestAlbum.TestArtist testArtist = new TestAlbum.TestArtist();
            testArtist.setName(item.name);
            TestAlbum.TestMusic testMusic = new TestAlbum.TestMusic(item.musicId, item.coverImg, item.url, item.title, testArtist);
            musics.add(testMusic);
        }
        TestAlbum testAlbum = new TestAlbum("", "", "", null, null, musics);
        PlayerManager.getInstance().loadAlbum(testAlbum);
        return testAlbum;
    }

}
