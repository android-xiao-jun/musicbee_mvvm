package com.musichive.main.utils;

import android.app.Application;
import android.content.Context;


/**
 * @Author Jun
 * Date 2021 年 05月 28 日 10:43
 * Description 这里包装一次 toast 提示 框架 方便更换
 */
public class ToastUtils {

    private static Context applicationContext;

    public static void init(Application context) {
        applicationContext = context.getApplicationContext();
        com.hjq.toast.ToastUtils.init(context);
//        com.hjq.toast.ToastUtils.setGravity(Gravity.BOTTOM ,0,0,0, SizeUtils.dp2px(50));
    }


    public static void showShort(String msg) {
        com.hjq.toast.ToastUtils.show(msg);
    }

    public static void showShort(int msg) {
        com.hjq.toast.ToastUtils.show(msg);
    }

    public static void showShort(CharSequence msg) {
        com.hjq.toast.ToastUtils.show(msg);
    }
}
