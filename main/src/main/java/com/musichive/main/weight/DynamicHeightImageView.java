package com.musichive.main.weight;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @Author Jun
 * Date 2021 年 06月 10 日 13:42
 * Description 音乐蜜蜂-mvvm版本
 */
public class DynamicHeightImageView extends RoundCornerImageView {

    private static final float DEFAULT_MIN_HEIGHT_RATIO = 0.5F;

    private boolean mLimitHeight = true;

    private double mHeightRatio = 1.0;
    private int mMaxHeight = -1;
    private int mMinHeight = -1;

    public DynamicHeightImageView(Context context) {
        super(context);
    }

    public DynamicHeightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicHeightImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLimitHeight(boolean limitHeight) {
        mLimitHeight = limitHeight;
    }

    public void setHeightRatio(double ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }

    public void setMinHeight(int minHeight) {
        mMinHeight = minHeight;
    }

    public void setMaxHeight(int maxHeight) {
        mMaxHeight = maxHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightRatio);

            if (mLimitHeight) {
                int newHeight = height;
                if (mMinHeight > 0) {
//                    mMinHeight = (int) (width * DEFAULT_MIN_HEIGHT_RATIO);
                    newHeight = Math.max(height, mMinHeight);
                }
                if (mMaxHeight > 0) {
                    newHeight = Math.min(newHeight, mMaxHeight);
                }
//                int newHeight = Math.min(Math.max(height, mMinHeight), mMaxHeight);
                setMeasuredDimension(width, newHeight);

            } else {
                setMeasuredDimension(width, height);
            }

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}