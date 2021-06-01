package com.musichive.common.other.float_player;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.SizeUtils;
import com.musichive.common.R;
import com.musichive.common.config.AppConfig;
import com.musichive.common.player.PlayerManager;

/**
 * @Author Jun
 * Date 2021年5月31日15:25:14
 * Description 悬浮播放view 包装（子view需要和悬浮顶部view边距问题统一处理）
 */
public class PlayerToolHelperView extends FrameLayout {

    private View childAt;
    private boolean current = false;
    private int viewRight = 15;

    public PlayerToolHelperView(@NonNull Context context) {
        this(context, null);
    }

    public PlayerToolHelperView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerToolHelperView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PlayerToolHelperView, defStyleAttr, 0);

        viewRight = a.getDimensionPixelSize(R.styleable.PlayerToolHelperView_viewRight, SizeUtils.dp2px(15));
        a.recycle();
    }

    private Observer<Boolean> clear = new Observer<Boolean>() {
        @Override
        public void onChanged(Boolean aBoolean) {
            if (aBoolean == current) {
                return;
            }
            current = aBoolean;
            upLayoutParams();
        }
    };


    private void upLayoutParams() {
        if (childAt == null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
        //显示 和 隐藏处理
        if (current || AppConfig.FLOAT_VIEW_TYPE == AppConfig.FLOAT_VIEW_TYPE_2) {
            layoutParams.setMarginEnd(viewRight);
            layoutParams.setMarginStart(0);
        } else {
            //44dp 10dp  （参考 view_layout_float_imp 布局）
            layoutParams.setMarginEnd(SizeUtils.dp2px(54));
        }
        childAt.setLayoutParams(layoutParams);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        PlayerManager.getInstance().getClearPlayListLiveData().observeForever(clear);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        PlayerManager.getInstance().getClearPlayListLiveData().removeObserver(clear);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 1) {
            throw new IllegalArgumentException("只能有一个子view");
        }
        childAt = getChildAt(0);
        upLayoutParams();
    }


}
