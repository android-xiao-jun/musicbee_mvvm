package com.musichive.main.other.fps;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Author Jun
 * Date 2021 年 02月 07 日 14:50
 * Description 检测fps布局
 */
public class FPSToolView extends FrameLayout {

    public FPSToolView(@NonNull Context context) {
        this(context, null);
    }

    public FPSToolView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FPSToolView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setVisibility(INVISIBLE);
    }

    public void initFpsLayout() {
        removeAllViews();
        TextView textView = new TextView(getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        addView(textView);
    }

    public void setFpsText(int fps) {
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
        TextView textView = (TextView) getChildAt(0);
        textView.setText("FPS " + fps);
        if (textView.getVisibility() != VISIBLE) {
            textView.setVisibility(VISIBLE);
        }
        if (fps < 40) {
            textView.setTextColor(Color.parseColor("#ff0000"));
        } else {
            textView.setTextColor(Color.parseColor("#333333"));
        }
    }
}
