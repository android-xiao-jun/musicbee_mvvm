package com.musichive.musicbee.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 10 日 16:47
 * Description 音乐蜜蜂-mvvm版本
 */
public class AdvertisementBean {
    @SerializedName("refreshCycle")
    @Expose
    private long refreshCycle;

    @SerializedName("certificatesCopy")
    @Expose
    private String certificatesCopy;

    @SerializedName("advertisements")
    @Expose
    private List<Advertisement> advertisements;

    public long getRefreshCycle() {
        return refreshCycle;
    }

    public void setRefreshCycle(long refreshCycle) {
        this.refreshCycle = refreshCycle;
    }

    public List<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(List<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public String getCertificatesCopy() {
        return certificatesCopy;
    }

    public void setCertificatesCopy(String certificatesCopy) {
        this.certificatesCopy = certificatesCopy;
    }
}
