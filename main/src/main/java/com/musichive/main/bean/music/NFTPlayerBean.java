package com.musichive.main.bean.music;

import java.io.Serializable;

/**
 * @Author Jun
 * Date 2021 年 06月 07 日 09:34
 * Description 音乐蜜蜂-mvvm版本
 */
public class NFTPlayerBean implements Serializable {

    /**
     * nftName : 元锦儿
     * brief : 民谣古风
     * tags : ["流行","摇滚","民谣","唱作人","轻音乐","中国特色"]
     * status : 2
     * permlink : pixbe-music-m35636625-1621200642066
     * creater : 0x59A252E14A8Cd76C3258341CA7FD22271ae0E61a
     * createrName : 邢立岩
     * owner : 0x59A252E14A8Cd76C3258341CA7FD22271ae0E61a
     * ownerName : 邢立岩
     * castPlatform : 音乐蜜蜂
     * castTime : 1622583189338
     * contractAddress : 0x4e7d6002bF98cD255cAB040E26433882620C45e0
     * tokenId : 2687258140970217
     * blockchainIdentify : heco
     * cover : default-img/4.jpg
     * duration : 180.5844898
     * musicEncodeUrl : https://1331-cn-north-4.cdn-vod.huaweicloud.com/asset/eb5251194855a8ea2529e6e4ef1a904c/play_video/a195ccb97d3af2db23400e9db8b68d1d.m3u8
     * musicPlayUrl : https://1331-cn-north-4.cdn-vod.huaweicloud.com/asset/eb5251194855a8ea2529e6e4ef1a904c/6ab6bd6b6b0bcf82c1f6921ab4c835e5.mp3
     * lyric : [00:16.170]她的名字 叫做元锦儿
     [00:23.917]她是一个 妖娆的戏子
     [00:32.199]青楼风尘 出淤泥而不染
     [00:40.159]浓妆艳抹 只为其一人
     [00:47.798]
     [00:49.246]他不是不知道 装作不想要
     [00:55.716]喜欢的还是好 不再有欢笑
     [01:03.402]心跳     煎熬    都在身边飘
     [01:11.131]争吵     唠叨    都变得美好
     [01:20.794]
     [01:52.118]她的名字 叫做元锦儿
     [02:00.090]她是一个 抱紧我的人
     [02:08.043]无聊时光 会逗我笑的人
     [02:16.278]陪我流泪 想爱不能爱
     [02:21.460]
     [02:23.817]我漫不经心的 进入你的生命
     [02:31.232]又不动声色的 毁了你的生活
     [02:39.197]心跳    煎熬     都在身边飘
     [02:47.374]拿起     放下     喜欢的还是喜欢
     * contentHash : a54ef05d80e687f4d4c7a069dc9595b41b01db0821406305b7816a6521731a07
     * musicVodId : eb5251194855a8ea2529e6e4ef1a904c
     */

    public String nftName;
    public String brief;
    public String tags;
    public int status;
    public String permlink;
    public String creater;
    public String createrName;
    public String owner;
    public String ownerName;
    public String castPlatform;
    public long castTime;
    public String contractAddress;
    public String tokenId;
    public String blockchainIdentify;
    public String cover;
    public double duration;
    public String musicEncodeUrl;
    public String musicPlayUrl;
    public String lyric;
    public String contentHash;
    public String musicVodId;

    public String getNftName() {
        return nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPermlink() {
        return permlink;
    }

    public void setPermlink(String permlink) {
        this.permlink = permlink;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreaterName() {
        return createrName;
    }

    public void setCreaterName(String createrName) {
        this.createrName = createrName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getCastPlatform() {
        return castPlatform;
    }

    public void setCastPlatform(String castPlatform) {
        this.castPlatform = castPlatform;
    }

    public long getCastTime() {
        return castTime;
    }

    public void setCastTime(long castTime) {
        this.castTime = castTime;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getBlockchainIdentify() {
        return blockchainIdentify;
    }

    public void setBlockchainIdentify(String blockchainIdentify) {
        this.blockchainIdentify = blockchainIdentify;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public String getMusicEncodeUrl() {
        return musicEncodeUrl;
    }

    public void setMusicEncodeUrl(String musicEncodeUrl) {
        this.musicEncodeUrl = musicEncodeUrl;
    }

    public String getMusicPlayUrl() {
        return musicPlayUrl;
    }

    public void setMusicPlayUrl(String musicPlayUrl) {
        this.musicPlayUrl = musicPlayUrl;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    public String getContentHash() {
        return contentHash;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    public String getMusicVodId() {
        return musicVodId;
    }

    public void setMusicVodId(String musicVodId) {
        this.musicVodId = musicVodId;
    }
}
