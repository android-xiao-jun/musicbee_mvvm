package com.musichive.common.other.float_player;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Choreographer;

import com.musichive.common.utils.HandlerUtils;


/**
 * @Author Jun
 * Date 2021年5月31日15:34:11
 * Description 检测fps工具（TODO 进度更新和监听页面数据更新）
 */
public final class PlayerTool implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "FPSTool";
    public static PlayerTool mInstance;

    private FrameRunnable frameRunnable = new FrameRunnable();
    private Application application;

    public static PlayerTool getInstance() {
        if (mInstance == null) {
            synchronized (PlayerTool.class) {
                if (mInstance == null) {
                    mInstance = new PlayerTool();
                }
            }
        }
        return mInstance;
    }

    public void start(Application c) {
        this.application = c;
        application.registerActivityLifecycleCallbacks(this);
        Log.e(TAG, "start");
        PlayerToolFloatUtils.get().add();
        PlayerToolFloatUtils.get().initFpsLayout();
        HandlerUtils.getInstance().getWorkHander().post(frameRunnable);
        Choreographer.getInstance().postFrameCallback(frameRunnable);
    }

    public void stop() {
        Log.e(TAG, "stop");
        PlayerToolFloatUtils.get().remove();
        HandlerUtils.getInstance().getWorkHander().removeCallbacks(frameRunnable);
        application.unregisterActivityLifecycleCallbacks(null);
        Choreographer.getInstance().postFrameCallback(null);
        application = null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        PlayerToolFloatUtils.get().attach(activity);
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        PlayerToolFloatUtils.get().detach(activity);
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    private class FrameRunnable implements Runnable, Choreographer.FrameCallback {

        long time = 0;
        int count = 0;

        @Override
        public void doFrame(long frameTimeNanos) {
            count++;
            Choreographer.getInstance().postFrameCallback(this);
        }

        @Override
        public void run() {
            long curTime = SystemClock.elapsedRealtime();
            if (time == 0) {
                // 第一次开始监控，跳过
            } else {
                int fps = (int) (1000.f * count / (curTime - time) + 0.5f);
                HandlerUtils.getInstance().getMainHander().post(() -> {
                    PlayerToolFloatUtils.get().loadPic();
                });
                String fpsStr = String.format("APP FPS is: %-3sHz", fps);
//                if (fps <= 50) {
//                    Log.e(TAG, fpsStr);
//                } else {
//                    Log.w(TAG, fpsStr);
//                }
            }
            count = 0;
            time = curTime;
            HandlerUtils.getInstance().getWorkHander().postDelayed(this, 1000);//1秒 检测一次
        }
    }

}
