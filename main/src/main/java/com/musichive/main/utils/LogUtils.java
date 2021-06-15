package com.musichive.main.utils;


import androidx.annotation.Nullable;

import com.socks.library.KLog;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 10:40
 * Description 这里包装一次 日志打印框架
 */
public class LogUtils {
    public static void init(boolean isShowLog, @Nullable String tag) {
        KLog.init(isShowLog, tag);
    }

    public static void d(Object msg) {
        KLog.i(msg);
    }

    public static void e(Object msg) {
        KLog.e(msg);
    }

    public static void e(String tag, Object... msg) {
        KLog.e(tag, msg);
    }

    public static void d(String tag, Object... msg) {
        KLog.d(tag, msg);
    }
    public static void json(String tag,String o) {
        KLog.json(tag,o);
    }

    public static void w(Object msg) {
        KLog.w(msg);
    }

}
