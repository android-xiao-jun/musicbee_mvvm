package com.musichive.common.player.helper;

import com.alibaba.fastjson.JSON;
import com.musichive.common.app.BaseApplication;
import com.musichive.common.bean.home.HomeDynamicInfo;
import com.musichive.common.bean.home.MusicLibBean;
import com.musichive.common.bean.home.MusicStateMusic;
import com.musichive.common.bean.music.MusicLibPlayerBean;
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
            TestAlbum.TestMusic testMusic;
            if (item.type == 5) {
                //乐库
                testArtist.setName(item.demoInfoVO.account);
                testMusic = new TestAlbum.TestMusic(item.demoInfoVO.permlink, item.getCoverLink()
                        , item.demoInfoVO.musicEncodeUrl, item.getDemoName(), testArtist);
                testMusic.setWorkType(item.demoInfoVO.worksType);
                testMusic.setType(0);
            } else {
                //市集
                testArtist.setName(item.account);
                testMusic = new TestAlbum.TestMusic(item.demoInfoVO.goodsId, item.getCoverLink()
                        , item.demoInfoVO.musicEncodeUrl, item.getDemoName(), testArtist);
                testMusic.setType(1);
            }

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
                musicEntity.account = item.account;
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
            musics.add(transformHomeMusic(item));
        }
        TestAlbum testAlbum = new TestAlbum("", "", "", null, null, musics);
        PlayerManager.getInstance().loadAlbum(testAlbum);
        return testAlbum;
    }

    /**
     * 从数据库实体类读取到播放列表
     */
    public static TestAlbum.TestMusic transformHomeMusic(MusicEntity item) {
        TestAlbum.TestArtist testArtist = new TestAlbum.TestArtist();
        testArtist.setName(item.name);
        testArtist.setAccount(item.account);
        TestAlbum.TestMusic testMusic = new TestAlbum.TestMusic(item.musicId, item.coverImg, item.url, item.title, testArtist);
        testMusic.setType(item.type);
        testMusic.setExpand(item.expand);
        return testMusic;
    }

    public static MusicEntity transformHomeMusic(TestAlbum.TestMusic item) {
        MusicEntity musicEntity = new MusicEntity();
        musicEntity.musicId = item.getMusicId();
        musicEntity.url = item.getUrl();
        musicEntity.coverImg = item.getCoverImg();
        musicEntity.title = item.getTitle();
        musicEntity.name = item.getTitle();
        musicEntity.account = item.getArtist().getAccount();
        musicEntity.type = item.getType();
        musicEntity.expand = item.getExpand();
        return musicEntity;
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
                musicEntity.url = item.musicEncodeUrl;
                musicEntity.coverImg = item.getCoverLink();
                musicEntity.title = item.getNftName();
                musicEntity.name = item.getCreaterName();
                musicEntity.account = "";//这个可以为空
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
            testArtist.setAccount("");//这个可以为空
            TestAlbum.TestMusic testMusic = new TestAlbum.TestMusic(String.valueOf(item.nftPostsId), item.getCoverLink(), item.musicEncodeUrl, item.getNftName(), testArtist);
            testMusic.setType(2);
            musics.add(testMusic);
        }
        TestAlbum testAlbum = new TestAlbum("", "", "", null, null, musics);
        PlayerManager.getInstance().loadAlbum(testAlbum, playIndex);
        return testAlbum;
    }

    /**
     * 乐库 和 播放列表转化
     *
     * @param item
     * @return
     */
    public static TestAlbum.TestMusic transformMusicLib(MusicLibPlayerBean item) {
        TestAlbum.TestArtist testArtist = new TestAlbum.TestArtist();
        testArtist.setName(item.getNickName());
        testArtist.setAccount(item.getAuthor());
        TestAlbum.TestMusic testMusic = new TestAlbum.TestMusic(item.getPermlink(), item.getCoverurl(), item.getMusic_encode_url(), item.getTitle(), testArtist);
        testMusic.setType(0);
        testMusic.setWorkType(item.getPostsType());
        testMusic.setExpand(JSON.toJSONString(item));
        return testMusic;
    }

    /**
     * 添加乐库歌曲 到播放器 并播放
     *
     * @param item
     */
    public static void addMusicLibAndPlay(MusicLibPlayerBean item) {
        List<TestAlbum.TestMusic> albumMusics = PlayerManager.getInstance().getAlbumMusics();
        if (albumMusics == null) {
            return;
        }
        TestAlbum.TestMusic testMusic = transformMusicLib(item);

        int oldIndex = -1;
        for (int i = 0; i < albumMusics.size(); i++) {
            if (testMusic.getMusicId().equals(albumMusics.get(i).getMusicId())) {
                oldIndex = i;
                break;
            }
        }
        if (oldIndex == -1) {
            MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().insertMusic(transformHomeMusic(testMusic));
            PlayerManager.getInstance().addPlayItemAndPlay(testMusic);
        } else {
            PlayerManager.getInstance().playAudio(oldIndex);
        }
    }

    /**
     * 乐库播放列表 添加 到播放器和数据库
     *
     * @param items
     * @param playIndex
     */
    public static void addMusicLibAndPlays(List<MusicLibPlayerBean> items, int playIndex) {
        List<TestAlbum.TestMusic> musics = new ArrayList<>();
        List<MusicEntity> musicEntities = new ArrayList<>();
        for (MusicLibPlayerBean item : items) {
            TestAlbum.TestMusic testMusic = transformMusicLib(item);
            MusicEntity musicEntity = transformHomeMusic(testMusic);
            musics.add(testMusic);
            musicEntities.add(musicEntity);
        }
        TestAlbum testAlbum = new TestAlbum("", "", "", null, null, musics);
        PlayerManager.getInstance().loadAlbum(testAlbum, playIndex);
        MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().deleteMusicAll();
        MusicDatabase.getInstance(BaseApplication.mInstance).musicDao().insertMusics(musicEntities);
    }
}
