/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.musichive.main.bean.music;

import com.kunminx.player.bean.base.BaseAlbumItem;
import com.kunminx.player.bean.base.BaseArtistItem;
import com.kunminx.player.bean.base.BaseMusicItem;

import java.util.List;

/**
 * Create by KunMinX at 19/10/31
 */
public class TestAlbum extends BaseAlbumItem<TestAlbum.TestMusic, TestAlbum.TestArtist> {

    private String albumMid;//专辑

    public TestAlbum() {

    }

    public TestAlbum(String goodsId, String demoName, String s, TestArtist testArtist, String coverLink, List<TestMusic> musics) {
        super(goodsId, demoName, s, testArtist, coverLink, musics);
    }

    public String getAlbumMid() {
        return albumMid;
    }

    public void setAlbumMid(String albumMid) {
        this.albumMid = albumMid;
    }

    /**
     * 每条播放数据
     */
    public static class TestMusic extends BaseMusicItem<TestArtist> {

        //0 乐库 1 市集 2 nft播放
        private int type = -1;

        private int workType = 0;

        private String expand = "{}";

        public int getWorkType() {
            return workType;
        }

        public void setWorkType(int workType) {
            this.workType = workType;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getExpand() {
            return expand;
        }

        public void setExpand(String expand) {
            this.expand = expand;
        }

        public int getType() {
            return type;
        }

        public TestMusic() {

        }

        public TestMusic(String musicId, String coverImg, String url, String title, TestArtist artist) {
            super(musicId, coverImg, url, title, artist);
        }

    }

    public static class TestArtist extends BaseArtistItem {
        //扩展--比如歌词，附加信息，标签 什么的

    }
}

