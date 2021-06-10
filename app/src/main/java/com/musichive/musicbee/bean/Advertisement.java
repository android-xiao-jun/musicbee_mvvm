package com.musichive.musicbee.bean;

import android.os.Parcel;
import android.os.Parcelable;


/**
 * @Author Jun
 * Date 2021 年 06月 10 日 16:47
 * Description 音乐蜜蜂-mvvm版本
 */
public class Advertisement implements Parcelable  {
    public static final int UN_DESTROY = 0;
    public static final int DESTROY = 1;

    public static final int STATUS_READ = 1;
    public static final int STATUS_UNREAD = 0;

    private int id;

    private String adType;

    private String androidMaxApp;

    private String androidMinApp;

    private String cover;

    private long createTime;

    private String displayLocation;

    private long displayTime;

    private long expireTime;

    private int interaction;

    private int oneshot;

    private int platform;

    private long publishTime;

    private int releaseState;

    private String target;

    private String title;

    private long updateTime;

    private int status;

    private long showTime;

    public Advertisement() {
    }

    protected Advertisement(Parcel in) {
        id = in.readInt();
        adType = in.readString();
        androidMaxApp = in.readString();
        androidMinApp = in.readString();
        cover = in.readString();
        createTime = in.readLong();
        displayLocation = in.readString();
        displayTime = in.readLong();
        expireTime = in.readLong();
        interaction = in.readInt();
        oneshot = in.readInt();
        platform = in.readInt();
        publishTime = in.readLong();
        releaseState = in.readInt();
        target = in.readString();
        title = in.readString();
        updateTime = in.readLong();
        status = in.readInt();
        showTime = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(adType);
        dest.writeString(androidMaxApp);
        dest.writeString(androidMinApp);
        dest.writeString(cover);
        dest.writeLong(createTime);
        dest.writeString(displayLocation);
        dest.writeLong(displayTime);
        dest.writeLong(expireTime);
        dest.writeInt(interaction);
        dest.writeInt(oneshot);
        dest.writeInt(platform);
        dest.writeLong(publishTime);
        dest.writeInt(releaseState);
        dest.writeString(target);
        dest.writeString(title);
        dest.writeLong(updateTime);
        dest.writeInt(status);
        dest.writeLong(showTime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Advertisement> CREATOR = new Parcelable.Creator<Advertisement>() {
        @Override
        public Advertisement createFromParcel(Parcel in) {
            return new Advertisement(in);
        }

        @Override
        public Advertisement[] newArray(int size) {
            return new Advertisement[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public String getAndroidMaxApp() {
        return androidMaxApp;
    }

    public void setAndroidMaxApp(String androidMaxApp) {
        this.androidMaxApp = androidMaxApp;
    }

    public String getAndroidMinApp() {
        return androidMinApp;
    }

    public void setAndroidMinApp(String androidMinApp) {
        this.androidMinApp = androidMinApp;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDisplayLocation() {
        return displayLocation;
    }

    public void setDisplayLocation(String displayLocation) {
        this.displayLocation = displayLocation;
    }

    public long getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(long displayTime) {
        this.displayTime = displayTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public int getInteraction() {
        return interaction;
    }

    public void setInteraction(int interaction) {
        this.interaction = interaction;
    }

    public int getOneshot() {
        return oneshot;
    }

    public void setOneshot(int oneshot) {
        this.oneshot = oneshot;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public int getReleaseState() {
        return releaseState;
    }

    public void setReleaseState(int releaseState) {
        this.releaseState = releaseState;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getShowTime() {
        return showTime;
    }

    public void setShowTime(long showTime) {
        this.showTime = showTime;
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((Advertisement) obj).getId();
    }

    /**
     * 判断广告是否过期
     *
     * @return: true: 过期， false：未过期
     */
    public boolean isExpire() {
        return System.currentTimeMillis() > expireTime;
    }

    /**
     * 判断全屏广告是否显示
     */
    public boolean isShow(long refreshCycle) {
        long currentTime = System.currentTimeMillis();
        if (expireTime > currentTime && publishTime < currentTime
                && currentTime < refreshCycle + showTime
                && status == STATUS_UNREAD) {
            return true;
        }
        return false;
    }

    /**
     * 判断全屏广告是否在当前生命周期内
     */
    public boolean isRefreshAdvertisement(long refreshCycle) {
        long currentTime = System.currentTimeMillis();
        if (currentTime > refreshCycle + showTime) {
            return true;
        }
        return false;
    }
}
