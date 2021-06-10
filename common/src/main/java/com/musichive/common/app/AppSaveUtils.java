package com.musichive.common.app;


import android.app.Application;
import android.os.Parcelable;

import com.tencent.mmkv.MMKV;

/**
 * @Author Jun
 * Date 2021 年 06月 10 日 16:55
 * Description app 用于保存相关数据
 */
public class AppSaveUtils {
    private MMKV kv;
    public volatile static AppSaveUtils INSTANCE;

    public static final String KYE_FIRST_INSTALL_TIME = "KYE_FIRST_INSTALL_TIME";

    public static final String KYE_SPLASH_AD = "KYE_SPLASH_AD";

    public static AppSaveUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (AppSaveUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AppSaveUtils();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Application application) {
        MMKV.initialize(application);
        kv = MMKV.defaultMMKV();

        long install_time = getLong(KYE_FIRST_INSTALL_TIME);
        if (install_time == 0) {
            saveLong(KYE_FIRST_INSTALL_TIME, System.currentTimeMillis());
        }
    }

    private AppSaveUtils() {

    }

    private void checkNull() {
        if (kv == null) {

        }
    }

    public boolean saveLong(String key, long value) {
        checkNull();
        return kv.encode(key, value);
    }

    public boolean saveString(String key, String value) {
        checkNull();
        return kv.encode(key, value);
    }

    public boolean saveBool(String key, boolean value) {
        checkNull();
        return kv.encode(key, value);
    }

    public boolean saveParcelable(String key, Parcelable value) {
        checkNull();
        return kv.encode(key, value);
    }

    public long getLong(String key) {
        checkNull();
        return kv.decodeLong(key);
    }

    public String getString(String key) {
        checkNull();
        return kv.decodeString(key);
    }

    public boolean getBool(String key) {
        checkNull();
        return kv.decodeBool(key);
    }

    public <T extends Parcelable> T getParcelable(String key, Class<T> tClass) {
        checkNull();
        return kv.decodeParcelable(key, tClass);
    }
}
