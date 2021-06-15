package com.musichive.main.bean.nft;


import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import com.alibaba.fastjson.JSON;
import com.musichive.main.config.AppConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 19 日 16:47
 * Description android_client
 */
public class HomeNFTBean implements Serializable {


    /**
     * nftPostsId : 21
     * creater : 0x59A252E14A8Cd76C3258341CA7FD22271ae0E61a
     * createrName : 邢立岩
     * owner : 0x59A252E14A8Cd76C3258341CA7FD22271ae0E61a
     * ownerName : 邢立岩
     * brief : 民谣古风
     * tags : ["流行","摇滚","民谣","唱作人","轻音乐","中国特色"]
     * tokenId : 2687258140970217
     * blockchainIdentify : heco
     * nftName : 元锦儿
     * cover : default-img/4.jpg
     * musicEncodeUrl : https://1331-cn-north-4.cdn-vod.huaweicloud.com/asset/eb5251194855a8ea2529e6e4ef1a904c/play_video/a195ccb97d3af2db23400e9db8b68d1d.m3u8
     * permlink : pixbe-music-m35636625-1621200642066
     * castPlatform : 音乐蜜蜂
     * castTime : 1622583189338
     * contractAddress : 0x4e7d6002bF98cD255cAB040E26433882620C45e0
     * contentHash : a54ef05d80e687f4d4c7a069dc9595b41b01db0821406305b7816a6521731a07
     */

    public int nftPostsId;
    public String creater;
    public String createrName;
    public String owner;
    public String ownerName;
    public String brief;
    public String tags;
    public String tokenId;
    public String blockchainIdentify;
    public String nftName;
    public String cover;
    public String musicEncodeUrl;
    public String permlink;
    public String castPlatform;
    public long castTime;
    public String contractAddress;
    public String contentHash;
    public int type;
    public String externalUrl;

    public int getType() {
        return type;
    }

    public String getMusicEncodeUrl() {
        return musicEncodeUrl;
    }

    public void setMusicEncodeUrl(String musicEncodeUrl) {
        this.musicEncodeUrl = musicEncodeUrl;
    }

    public String getPermlink() {
        return permlink;
    }

    public void setPermlink(String permlink) {
        this.permlink = permlink;
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

    public String getContentHash() {
        return contentHash;
    }

    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getCover() {
        return cover;
    }

    public String getCoverLink() {
        return AppConfig.URLPREFIX + cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getNftName() {
        return nftName == null ? "" : nftName;
    }

    public void setNftName(String nftName) {
        this.nftName = nftName;
    }

    public String getBlockchainIdentify() {
        return blockchainIdentify;
    }

    public void setBlockchainIdentify(String blockchainIdentify) {
        this.blockchainIdentify = blockchainIdentify;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    public int getNftPostsId() {
        return nftPostsId;
    }

    public void setNftPostsId(int nftPostsId) {
        this.nftPostsId = nftPostsId;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getItemName() {
        return "创作者：" + getCreaterName() + " / 拥有者：" + getOwnerName();
    }

    public String getItemTokenSHow() {
        return "Token ID：" + getTokenId();
    }

    public String getItemChainSHow() {
        return "区块链：" + getBlockchainIdentify();
    }

    public SpannableString getItemTagShow() {
        StringBuilder temp1 = new StringBuilder();
        if (getTags() != null) {
            List<String> split = jsonToListString(getTags());
            for (String s : split) {
                temp1.append("#").append(s).append(" ");//#流行 #电子
            }
        }
        temp1.append(" ");
        String temp2 = getBrief();
        SpannableString spannableString = new SpannableString(temp1.toString() + temp2);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#77C2FB"))
                , 0, temp1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    public static List<String> jsonToListString(String string) {
        try {
            return JSON.parseObject(string, List.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }
}
