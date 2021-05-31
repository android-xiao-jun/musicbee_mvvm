package com.musichive.common.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * @Author Jun
 * Date 2021 年 01月 29 日 10:59
 * Description android_client
 */
public class HandlerUtils {
    public static HandlerUtils instance = null;

    public static HandlerUtils getInstance() {
        if (instance == null) {
            synchronized (HandlerUtils.class) {
                if (instance == null) {
                    instance = new HandlerUtils();
                }
            }
        }
        return instance;
    }

    private Handler handler;//串行
    private Handler workHandler;//串行

    private HandlerUtils() {
        handler = new Handler(Looper.getMainLooper());

        HandlerThread handlerThread=new HandlerThread("work");
        handlerThread.start();
        workHandler=new Handler(handlerThread.getLooper());

    }

    public Handler getMainHander() {
        return handler;
    }

    public void postMain(Runnable r) {
        handler.post(r);
    }

    public Handler getWorkHander() {
        return workHandler;
    }

    public void postWork(Runnable r) {
        workHandler.post(r);
    }


    public void clearMainTask(){
        handler.removeCallbacksAndMessages(null);
    }

    public void clearWorkTask(){
        workHandler.removeCallbacksAndMessages(null);
    }
}
