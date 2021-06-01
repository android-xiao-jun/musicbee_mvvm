package com.musichive.common.other.float_player;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.core.view.ViewCompat;

import com.blankj.utilcode.util.BarUtils;
import com.kunminx.player.bean.dto.ChangeMusic;
import com.kunminx.player.bean.dto.PlayingMusic;
import com.musichive.common.app.BaseApplication;
import com.musichive.common.config.AppConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021年6月1日09:26:56
 * Description 悬浮播放（右上角小圆圈）
 */
public class PlayerToolFloatUtils {
    private static volatile PlayerToolFloatUtils mInstance;
    private List<PlayerToolViewDataListener> playerToolViews;
    private WeakReference<ViewGroup> mContainer;

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
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView == null) {
                return;
            }
            if (ViewCompat.isAttachedToWindow(playerToolView) && getContainer() != null) {
                getContainer().removeView(playerToolView);
            }
            playerToolViews.remove(playerToolView);
        }
    }

    private void ensureFloatingView() {
        synchronized (this) {
            if (playerToolViews != null) {
                return;
            }
            playerToolViews = new ArrayList<>();

            if (AppConfig.FLOAT_VIEW_TYPE == AppConfig.FLOAT_VIEW_TYPE_2) {
                PlayerToolTwoView playerToolView = new PlayerToolTwoView(BaseApplication.mInstance);
                playerToolView.setLayoutParams(getParams(playerToolView,2));
                playerToolViews.add(playerToolView);
            } else if (AppConfig.FLOAT_VIEW_TYPE == AppConfig.FLOAT_VIEW_TYPE_0) {
                PlayerToolTwoView playerToolView2 = new PlayerToolTwoView(BaseApplication.mInstance);
                PlayerToolView playerToolView1 = new PlayerToolView(BaseApplication.mInstance);
                playerToolView2.setLayoutParams(getParams(playerToolView2,2));
                playerToolView1.setLayoutParams(getParams(playerToolView1,1));
                playerToolViews.add(playerToolView2);
                playerToolViews.add(playerToolView1);
            } else {
                PlayerToolView playerToolView = new PlayerToolView(BaseApplication.mInstance);
                playerToolView.setLayoutParams(getParams(playerToolView,1));
                playerToolViews.add(playerToolView);
            }
            for (PlayerToolViewDataListener playerToolView : playerToolViews) {
                addViewToWindow(playerToolView);
            }
        }
    }

    private void addViewToWindow(final View view) {
        if (getContainer() == null) {
            return;
        }
        getContainer().addView(view);
    }

    private ViewGroup getContainer() {
        if (mContainer == null) {
            return null;
        }
        return mContainer.get();
    }

    private FrameLayout.LayoutParams getParams(ViewGroup viewGroup,int type) {
        if (type == 1) {
            int actionBarHeight = BarUtils.getActionBarHeight();
            int statusBarHeight = BarUtils.getStatusBarHeight();
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, actionBarHeight);
            params.gravity = Gravity.END;
            params.setMargins(0, statusBarHeight, 0, statusBarHeight);
            return params;
        } else if (type == 2) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) viewGroup.getLayoutParams();
            params.gravity = Gravity.BOTTOM;
            return params;
        } else {
            return null;
        }

    }

    public void attach(Activity activity, ViewGroup viewGroup) {
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView instanceof PlayerToolTwoView) {
                attach(viewGroup, playerToolView);
            } else {
                attach(activity, playerToolView);
            }
        }
    }

    public void attach(Activity activity, PlayerToolViewDataListener playerToolView) {
        attach(getActivityRoot(activity), playerToolView);
    }

    public void attach(ViewGroup container, PlayerToolViewDataListener playerToolView) {
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
        if (playerToolView instanceof PlayerToolTwoView) {
            playerToolView.setLayoutParams(getParams(playerToolView,2));
        } else {
            playerToolView.setLayoutParams(getParams(playerToolView,1));
        }
        addViewToWindow(playerToolView);
    }

    public void detach(Activity activity, ViewGroup viewGroup) {
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView instanceof PlayerToolTwoView) {
                detach(viewGroup, playerToolView);
            } else {
                detach(activity, playerToolView);
            }
        }
    }

    public void detach(Activity activity, PlayerToolViewDataListener playerToolView) {
        detach(getActivityRoot(activity), playerToolView);
    }

    public void detach(ViewGroup container, PlayerToolViewDataListener playerToolView) {
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
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView != null) {
                playerToolView.initFpsLayout();
            }
        }
    }

    public void loadPic(Object pic) {
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView != null) {
                playerToolView.loadPic(pic);
            }
        }
    }

    public void startAnimation(boolean b) {
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView != null) {
                playerToolView.rotateAnimation(b);
            }
        }
    }

    public void setOnClickListener(View.OnClickListener listener) {
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView != null) {
                playerToolView.setFloatClick(listener);
            }
        }
    }

    public void upData(ChangeMusic changeMusic) {
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView != null) {
                playerToolView.upData(changeMusic);
            }
        }
    }

    public void upDataProgress(PlayingMusic playingMusic) {
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView != null) {
                playerToolView.upDataProgress(playingMusic);
            }
        }
    }

    public void upPlayStatus(boolean play) {
        for (PlayerToolViewDataListener playerToolView : playerToolViews) {
            if (playerToolView != null) {
                playerToolView.upPlayStatus(play);
            }
        }
    }
}
