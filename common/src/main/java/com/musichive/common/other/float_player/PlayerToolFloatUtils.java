package com.musichive.common.other.float_player;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;

import com.musichive.common.app.BaseApplication;

import java.lang.ref.WeakReference;

/**
 * @Author Jun
 * Date 2021 年 02月 07 日 14:53
 * Description 检测fps悬浮工具类
 */
public class PlayerToolFloatUtils {
    private static volatile PlayerToolFloatUtils mInstance;
    private PlayerToolView fpsToolView;
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
        if (fpsToolView == null) {
            return;
        }
        if (ViewCompat.isAttachedToWindow(fpsToolView) && getContainer() != null) {
            getContainer().removeView(fpsToolView);
        }
        fpsToolView = null;
    }

    private void ensureFloatingView() {
        synchronized (this) {
            if (fpsToolView != null) {
                return;
            }
            fpsToolView = new PlayerToolView(BaseApplication.mInstance);
            fpsToolView.setLayoutParams(getParams());
            addViewToWindow(fpsToolView);
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
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER | Gravity.START;
        params.setMargins(40, params.topMargin, 40, params.bottomMargin);
        return params;
    }

    public void attach(Activity activity) {
        attach(getActivityRoot(activity));
    }

    public void attach(FrameLayout container) {
        if (container == null || fpsToolView == null) {
            mContainer = new WeakReference<>(container);
            return;
        }
        if (fpsToolView.getParent() == container) {
            return;
        }
        if (fpsToolView.getParent() != null) {
            ((ViewGroup) fpsToolView.getParent()).removeView(fpsToolView);
        }
        mContainer = new WeakReference<>(container);
        container.addView(fpsToolView);
    }

    public void detach(Activity activity) {
        detach(getActivityRoot(activity));
    }

    public void detach(FrameLayout container) {
        if (fpsToolView != null && container != null && ViewCompat.isAttachedToWindow(fpsToolView)) {
            container.removeView(fpsToolView);
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
        if (fpsToolView != null) {
            fpsToolView.initFpsLayout();
        }
    }

    public void loadPic() {
        if (fpsToolView != null) {
            fpsToolView.loadPic();
        }
    }
}
