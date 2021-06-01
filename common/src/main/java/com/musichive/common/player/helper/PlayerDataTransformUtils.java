package com.musichive.common.player.helper;

import com.musichive.common.bean.home.HomeDynamicInfo;
import com.musichive.common.bean.music.TestAlbum;
import com.musichive.common.player.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 01 日 09:35
 * Description 播放音频数据转换model
 */
public class PlayerDataTransformUtils {

    public static TestAlbum transformHomeMusic(HomeDynamicInfo.ListBean item) {
        return transformHomeMusic(Collections.singletonList(item),0);
    }

    public static TestAlbum transformHomeMusic(List<HomeDynamicInfo.ListBean> list,int playIndex) {
        List<TestAlbum.TestMusic> musics = new ArrayList<>();
        for (HomeDynamicInfo.ListBean item : list) {
            TestAlbum.TestArtist testArtist = new TestAlbum.TestArtist();
            testArtist.setName(item.name);
            TestAlbum.TestMusic testMusic = new TestAlbum.TestMusic(item.demoInfoVO.goodsId, item.getCoverLink(), item.demoInfoVO.musicEncodeUrl, item.getDemoName(), testArtist);
            musics.add(testMusic);
        }
        TestAlbum testAlbum = new TestAlbum("", "", "", null, null, musics);
        PlayerManager.getInstance().loadAlbum(testAlbum,playIndex);
        return testAlbum;
    }

}
