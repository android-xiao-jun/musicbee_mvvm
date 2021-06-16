package com.musichive.main.config;

import android.os.Environment;
import android.provider.Settings;

import com.kunminx.architecture.utils.Utils;
import com.musichive.main.app.BaseApplication;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 09:17
 * Description 音乐蜜蜂-mvvm版本
 */
public class AppConfig {


    public static final class NetWork {
        //首页及其他接口
        public static final String BASE_URL = "http://server.musicbee.com.cn/";
        //商城接口
        public static final String BASE_URL2 = "http://server.musichive.com.cn/";
        public static final String BASE_WEB_PAGE_URL = "http://www.musicbee.com.cn/";
        public static final String BASE_WEB_PAGE_URL2 = "http://www.musichive.com.cn/";
        //连接时间增加
        public static final long connectTimeoutMills = 15 * 1000l;
        public static final long readTimeoutMills = 15 * 1000l;

        public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
        public static final String ACCEPT = "application/json;charset=UTF-8";
        public static final String ACCEPT_ENCODING = "identity";
    }

    /**
     * 首页底部位置配置
     */
    public static final class HomeTab {
        public final static int HOME_BOTTOM_CENTER_INDEX = 2;

        /**
         * 注意修改fragment先后顺序需要修改这里
         */
        public final static int HOME_TAB_INDEX = 0;
        public final static int HOME_NFT_TAB_INDEX = 1;
        public final static int HOME_MARKET_TAB_INDEX = 2;
        public final static int HOME_WORKS_TAB_INDEX = 3;
        public final static int HOME_ME_TAB_INDEX = 4;
    }

    public static final class Device {
        public final static String Android_ID = Settings.Secure.getString(BaseApplication.mInstance.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


    public static final String COVER_PATH = Utils.getApp().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();

    public static String URLPREFIX = "";

    public static final int REQUEST_LIMIT = 12;

    /**
     * 悬浮相关参数
     */
    public static final int FLOAT_VIEW_TYPE_1 = 0;
    public static final int FLOAT_VIEW_TYPE_2 = 1;
    public static final int FLOAT_VIEW_TYPE_0 = -1;
    public static  int FLOAT_VIEW_TYPE = FLOAT_VIEW_TYPE_0;//0 显示 顶部悬浮  1 显示底部悬浮 -1都显示

}
