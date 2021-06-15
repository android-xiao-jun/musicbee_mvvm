package com.musichive.main.config;

import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.musichive.main.utils.NetworkManager;

import java.util.Locale;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 16:41
 * Description 音乐蜜蜂-mvvm版本
 */
public class HttpConstants {

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String HEADER_USER_AGENT = "User-Agent";

    public static final String HEADER_IDFD = "PBClientID";

    public static final String HEADER_API_VERSION = "api-version";


    public static final String REQUEST_PARAM_PLATFORM = "platform";

    public static final String REQUEST_PARAM_APP_VERSION = "appVersion";

    public static final String REQUEST_PARAM_OS_VERSION = "osVersion";

    public static final String REQUEST_PARAM_COUNTRY = "country";

    public static final String REQUEST_PARAM_LANGUAGE = "language";

    public static final String REQUEST_PARAM_DEVICE_NAME = "deviceName";

    public static final String REQUEST_PARAM_APP_NAME = "appName";

    public static final String TIMEZONE = "timezone";

    public static final String TIMESTAMP = "timestamp";

    public static final String CHANNEL = "channel";


    public static final String REQUEST_PARAM_SIGNATURE = "signature";

    public static final String buildHeader(String accessToken) {
        return "bearer " + accessToken;
    }

    public static final String NETWORK_4G = "4G";

    public static final String NETWORK_NONE = "NONE";

    public static final String NETWORK_WIFI = "WIFI";

    private static String DEVICE_NAME = null;

    private static String LANGUAGE_COUNTRY = null;

    private static String SCREEN = null;

    public static String getShortAppVersionName() {
        String appVersion = AppUtils.getAppVersionName();
        String[] versions = appVersion.split("\\.");
        if (versions.length > 3) {
            appVersion = appVersion.substring(0, appVersion.lastIndexOf("."));
        }
        return appVersion;
    }

    public static final String getDeviceName() {
        if (DEVICE_NAME == null) {
            DEVICE_NAME = Build.BRAND + " " + Build.MODEL;
        }
        return DEVICE_NAME;
    }

    public static final String getScreen() {
        if (SCREEN == null) {
            if (ScreenUtils.getScreenWidth() > 0 && ScreenUtils.getScreenHeight() > 0) {
                SCREEN = ScreenUtils.getScreenWidth() + "x" + ScreenUtils.getScreenHeight();
            }
        }
        return SCREEN;
    }

    public static final String getLanguageCountry() {
        if (LANGUAGE_COUNTRY == null) {
            Locale locale = Locale.getDefault();
            String language = locale.getLanguage();
            if (!TextUtils.isEmpty(language) && language.equalsIgnoreCase("in")) {
                language = "id";
            }
            String langCountry = language;
            if (!TextUtils.isEmpty(locale.getCountry())) {
                langCountry = language + "_" + locale.getCountry().toUpperCase();
            }
            LANGUAGE_COUNTRY = langCountry;
        }
        return LANGUAGE_COUNTRY;
    }

    public static String buildUserAgent() {

        String osVersion = Build.VERSION.RELEASE;
        NetworkManager.NetworkStatus networkStatus = NetworkManager.getInstance().getCurrentStatus();
        String networkType = NETWORK_NONE;
        if (networkStatus == NetworkManager.NetworkStatus.WIFI) {
            networkType = NETWORK_WIFI;
        } else if (networkStatus == NetworkManager.NetworkStatus.MOBILE) {
            networkType = NETWORK_4G;
        }
        //"PxBee/0.9.2 (iOS; 10.1.2; zh_CN; iphone5,1; network=WIFI; screen=320x480)"
        return String.format("PxBee/%s (Android; %s; %s; %s; network=%s; screen=%s)", getShortAppVersionName(), osVersion, getLanguageCountry(),
                getDeviceName(), networkType, getScreen());
    }
}
