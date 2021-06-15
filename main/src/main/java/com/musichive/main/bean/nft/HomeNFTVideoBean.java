package com.musichive.main.bean.nft;

import com.musichive.main.BuildConfig;

/**
 * @Author Jun
 * Date 2021 年 06月 02 日 09:39
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeNFTVideoBean {
    public String nftPic;
    public String nftVideo;
    public String nftinfo;

    public String getPic1() {
        if (nftPic == null || "".equals(nftPic)) {
            if (BuildConfig.DEBUG) {
                return "https://ll-test-musicbee-image.obs.cn-north-4.myhuaweicloud.com/nft/nft_home_video_bg.webp";
            }
            return "https://ll-musicbee-image.obs.cn-north-4.myhuaweicloud.com/nft/nft_home_video_bg.webp";
        }
        return nftPic;
    }

    public void setPic1(String nftPic) {
        this.nftPic = nftPic;
    }

    public String getUrl1() {
        if (nftVideo == null || "".equals(nftVideo)) {
            if (BuildConfig.DEBUG) {
                return "https://ll-test-musicbee-image.obs.cn-north-4.myhuaweicloud.com/nft/bgmovie.mp4";
            }
            return "https://ll-musicbee-image.obs.cn-north-4.myhuaweicloud.com/nft/bgmovie.mp4";
        }
        return nftVideo;
    }

    public void setUrl1(String nftVideo) {
        this.nftVideo = nftVideo;
    }

    public String getNftinfo() {
        if (nftinfo == null || "".equals(nftinfo)) {
            if (BuildConfig.DEBUG) {
                return "https://ll-test-musicbee-image.obs.cn-north-4.myhuaweicloud.com/nft/nftinfo.webp";
            }
            return "https://ll-musicbee-image.obs.cn-north-4.myhuaweicloud.com/nft/nftinfo.webp";
        }
        return nftinfo;
    }
}
