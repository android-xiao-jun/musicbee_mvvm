package com.musichive.common.other.float_player;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;

import com.blankj.utilcode.util.BarUtils;
import com.musichive.common.app.BaseApplication;

import java.lang.ref.WeakReference;

/**
 * @Author Jun
 * Date 2021年6月1日09:26:56
 * Description 悬浮播放（右上角小圆圈）
 */
public class PlayerToolFloatUtils {
    private static volatile PlayerToolFloatUtils mInstance;
    private PlayerToolView playerToolView;
    private WeakReference<FrameLayout> mContainer;

    private PlayerToolFloatUtils() {
    }

    public static PlayerToolFloatUtils get() {
        if (mInstance == null) {
            synchronized (PlayerToolFloatUtils.class) {
                if (mInstance == null) {
                    mInstance = new PlayerToolFloatUtils();
                }
            }
        }
        return mInstance;
    }

    public void add() {
        ensureFloatingView();
    }

    public void remove() {
        if (playerToolView == null) {
            return;
        }
        if (ViewCompat.isAttachedToWindow(playerToolView) && getContainer() != null) {
            getContainer().removeView(playerToolView);
        }
        playerToolView = null;
    }

    private void ensureFloatingView() {
        synchronized (this) {
            if (playerToolView != null) {
                return;
            }
            playerToolView = new PlayerToolView(BaseApplication.mInstance);
            playerToolView.setLayoutParams(getParams());
            addViewToWindow(playerToolView);
        }
    }

    private void addViewToWindow(final View view) {
        if (getContainer() == null) {
            return;
        }
        getContainer().addView(view);
    }

    private FrameLayout getContainer() {
        if (mContainer == null) {
            return null;
        }
        return mContainer.get();
    }

    private FrameLayout.LayoutParams getParams() {
        int actionBarHeight = BarUtils.getActionBarHeight();
        int statusBarHeight = BarUtils.getStatusBarHeight();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, actionBarHeight);
        params.gravity = Gravity.END;
        params.setMargins(0, statusBarHeight, 0, statusBarHeight);
        return params;
    }

    public void attach(Activity activity) {
        attach(getActivityRoot(activity));
    }

    public void attach(FrameLayout container) {
        if (container == null || playerToolView == null) {
            mContainer = new WeakReference<>(container);
            return;
        }
        if (playerToolView.getParent() == container) {
            return;
        }
        if (playerToolView.getParent() != null) {
            ((ViewGroup) playerToolView.getParent()).removeView(playerToolView);
        }
        mContainer = new WeakReference<>(container);
        playerToolView.setLayoutParams(getParams());
        addViewToWindow(playerToolView);
    }

    public void detach(Activity activity) {
        detach(getActivityRoot(activity));
    }

    public void detach(FrameLayout container) {
        if (playerToolView != null && container != null && ViewCompat.isAttachedToWindow(playerToolView)) {
            container.removeView(playerToolView);
        }
        if (getContainer() == container) {
            mContainer = null;
        }
    }

    private FrameLayout getActivityRoot(Activity activity) {
        if (activity == null) {
            return null;
        }
        try {
            return (FrameLayout) activity.getWindow().getDecorView().findViewById(android.R.id.content);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void initFpsLayout() {
        if (playerToolView != null) {
            playerToolView.initFpsLayout();
        }
    }

    public void loadPic(Object pic) {
        if (playerToolView != null) {
            playerToolView.loadPic(pic);
        }
    }

    public void startAnimation(boolean b) {
        if (playerToolView != null) {
            playerToolView.rotateAnimation(b);
        }
    }

    public void setOnClickListener(View.OnClickListener listener){
        if (playerToolView != null) {
            playerToolView.setFloatClick(listener);
        }
    }
}
