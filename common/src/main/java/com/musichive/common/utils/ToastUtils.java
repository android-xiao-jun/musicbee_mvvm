package com.musichive.common.utils;

import android.content.Context;


/**
 * @Author Jun
 * Date 2021 年 05月 28 日 10:43
 * Description 这里包装一次 toast 提示 框架 方便更换
 */
public class ToastUtils {

    private static Context applicationContext;

    public static void init(Context context) {
        applicationContext = context.getApplicationContext();
    }


    public static void showShort(String msg) {

    }
}
