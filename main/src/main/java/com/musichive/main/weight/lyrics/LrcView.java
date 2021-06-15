package com.musichive.main.weight.lyrics;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Looper;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;

import com.blankj.utilcode.util.EncryptUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 18 日 14:27
 * Description 歌词
 */
public class LrcView extends View {

    private static final String DEFAULT_CONTENT = "没有歌词";
    private List<LyricBean> mLrcData;
    private TextPaint mTextPaint;
    private String mDefaultContent;
    private int mCurrentLine;
    private float mOffset;
    private float mLastMotionX;
    private float mLastMotionY;
    private int mScaledTouchSlop;
    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;
    private int mMaximumFlingVelocity;
    private int mMinimumFlingVelocity;
    private float mLrcTextSize;
    private float mCurrentPlayLrcTextSize;
    private float mLrcLineSpaceHeight;
    private int mTouchDelay;
    private int mNormalColor;
    private int mCurrentPlayLineColor;
    private float mNoLrcTextSize;
    private int mNoLrcTextColor;
    //是否拖拽中，否的话响应onClick事件
    private boolean isDragging;
    //用户开始操作
    private boolean isUserScroll;
    private boolean isAutoAdjustPosition = true;
    //是否有时间戳
    boolean isGunDongGeCi = false;
    private float topOffset = 23;
    private int type;


    public LrcView(Context context) {
        this(context, null);
    }

    public LrcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LrcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
//        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.LrcView);
//        type = ta.getInt(R.styleable.LrcView_type, 0x01);
        if (type == 0x02) {
            mNormalColor = Color.parseColor("#4dffffff");
            mCurrentPlayLineColor = Color.parseColor("#ffffffff");
            mNoLrcTextColor = Color.parseColor("#ffffffff");
        } else {
            mNormalColor = Color.parseColor("#333333");
            mCurrentPlayLineColor = Color.parseColor("#1e1e1e");
            mNoLrcTextColor = Color.parseColor("#333333");
        }
        mLrcTextSize = sp2px(context, 13);
        topOffset = sp2px(context, 50);
        mCurrentPlayLrcTextSize = sp2px(context, 20);
        mLrcLineSpaceHeight = dp2px(context, 8);
        mTouchDelay = 3500;
        mNoLrcTextSize = dp2px(context, 20);
        setupConfigs(context);
//        ta.recycle();
    }

    private void setupConfigs(Context context) {
        mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumFlingVelocity = ViewConfiguration.get(context).getScaledMaximumFlingVelocity();
        mMinimumFlingVelocity = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();
        mOverScroller = new OverScroller(context, new DecelerateInterpolator());
        mOverScroller.setFriction(0.1f);

        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        mDefaultContent = DEFAULT_CONTENT;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    private int getLrcWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getLrcHeight() {
        return getHeight();
    }

    private boolean isLrcEmpty() {
        return mLrcData == null || getLrcCount() == 0;
    }

    private int getLrcCount() {
        return mLrcData.size();
    }

    public void setLrcData(List<LyricBean> lrcData) {
        resetView(DEFAULT_CONTENT);
        mLrcData = lrcData;
        invalidate();
    }

    public void setTypeFace(Typeface typeface) {
        if (mTextPaint != null) {
            mTextPaint.setTypeface(typeface);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isLrcEmpty()) {
            drawEmptyText(canvas);
            return;
        }
        float y = topOffset;
        float x = getPaddingLeft();
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        for (int i = 0; i < getLrcCount(); i++) {
            if (i > 0) {
                y += (getTextHeight(i - 1) + getTextHeight(i)) / 2f + mLrcLineSpaceHeight;
            }
            if (mCurrentLine == i) {
                mTextPaint.setColor(mCurrentPlayLineColor);
                mTextPaint.setFakeBoldText(true);
                mTextPaint.setTextSize(mCurrentPlayLrcTextSize);
            } else {
                mTextPaint.setFakeBoldText(false);
                mTextPaint.setColor(mNormalColor);
                mTextPaint.setTextSize(mLrcTextSize);
            }
            drawLrc(canvas, x, y, i);
        }

    }

    private HashMap<String, StaticLayout> mLrcMap = new HashMap<>();

    private void drawLrc(Canvas canvas, float x, float y, int i) {
        String text = mLrcData.get(i).getLyric();
        Layout.Alignment temp;
        if (type == 0x02) {
            temp = Layout.Alignment.ALIGN_NORMAL;
        } else {
            temp = Layout.Alignment.ALIGN_CENTER;
        }
        StaticLayout staticLayout;
        if (mCurrentLine == i) {
            staticLayout = new StaticLayout(text, mTextPaint, getLrcWidth(),
                    temp, 1f, 0f, false);
        } else if (mLrcMap.get(text) != null) {
            staticLayout = mLrcMap.get(text);
        } else {
            staticLayout = new StaticLayout(text, mTextPaint, getLrcWidth(),
                    temp, 1f, 0f, false);
            mLrcMap.put(text, staticLayout);
        }
        canvas.save();
        canvas.translate(x, y - staticLayout.getHeight() / 2f - mOffset);
        staticLayout.draw(canvas);
        canvas.restore();
    }


    //中间空文字
    private void drawEmptyText(Canvas canvas) {
        Layout.Alignment temp;
        if (type == 0x02) {
            temp = Layout.Alignment.ALIGN_NORMAL;
        } else {
            temp = Layout.Alignment.ALIGN_CENTER;
        }
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setColor(mNoLrcTextColor);
        mTextPaint.setTextSize(mNoLrcTextSize);
        canvas.save();
        StaticLayout staticLayout = new StaticLayout(mDefaultContent, mTextPaint,
                getLrcWidth(), temp, 1f, 0f, false);
        canvas.translate(0, (getHeight()-staticLayout.getHeight())/2);
        staticLayout.draw(canvas);
        canvas.restore();
    }

    public void updateTime(long time) {
        if (isLrcEmpty()) {
            return;
        }
        int linePosition = getUpdateTimeLinePosition(time);
        if (mCurrentLine != linePosition) {
            mCurrentLine = linePosition;
            if (isUserScroll) {
                invalidateView();
                return;
            }
            ViewCompat.postOnAnimation(LrcView.this, mScrollRunnable);
        }
    }

    private int getUpdateTimeLinePosition(long time) {
        int linePos = 0;
        if (!isGunDongGeCi && mLrcData != null) {
            return linePos;
        }
        for (int i = 0; i < getLrcCount(); i++) {
            LyricBean lrc = mLrcData.get(i);
            if (time >= lrc.getTime()) {
                if (i == getLrcCount() - 1) {
                    linePos = getLrcCount() - 1;
                } else if (time < mLrcData.get(i + 1).getTime()) {
                    linePos = i;
                    break;
                }
            }
        }
        return linePos;
    }

    private Runnable mScrollRunnable = new Runnable() {
        @Override
        public void run() {
            isUserScroll = false;
            scrollToPosition(mCurrentLine);
        }
    };

    private void scrollToPosition(int linePosition) {
        float scrollY = getItemOffsetY(linePosition);
        final ValueAnimator animator = ValueAnimator.ofFloat(mOffset, scrollY);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffset = (float) animation.getAnimatedValue();
                invalidateView();
            }
        });
        animator.setDuration(300);
        animator.start();
    }

    private float getItemOffsetY(int linePosition) {
        float tempY = 0;
        for (int i = 1; i <= linePosition; i++) {
            tempY += (getTextHeight(i - 1) + getTextHeight(i)) / 2 + mLrcLineSpaceHeight;
        }
        return tempY;
    }

    private HashMap<String, StaticLayout> mStaticLayoutHashMap = new HashMap<>();

    private float getTextHeight(int linePosition) {
        if (mCurrentLine == linePosition) {
            mTextPaint.setFakeBoldText(true);
            mTextPaint.setTextSize(mCurrentPlayLrcTextSize);
        } else {
            mTextPaint.setFakeBoldText(false);
            mTextPaint.setTextSize(mLrcTextSize);
        }
        String text = mLrcData.get(linePosition).getLyric();
        StaticLayout staticLayout;
        Layout.Alignment temp;
        if (type == 0x02) {
            temp = Layout.Alignment.ALIGN_NORMAL;
        } else {
            temp = Layout.Alignment.ALIGN_CENTER;
        }
        if (mCurrentLine == linePosition) {
            staticLayout = new StaticLayout(text, mTextPaint,
                    getLrcWidth(), temp, 1f, 0f, false);
        } else if (mStaticLayoutHashMap.get(text) != null) {
            staticLayout = mStaticLayoutHashMap.get(text);
        } else {
            staticLayout = new StaticLayout(text, mTextPaint,
                    getLrcWidth(), temp, 1f, 0f, false);
            mStaticLayoutHashMap.put(text, staticLayout);
        }
        return staticLayout.getHeight();
    }

    private boolean overScrolled() {
        return mOffset > getItemOffsetY(getLrcCount() - 1) || mOffset < 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isLrcEmpty()) {
            return super.onTouchEvent(event);
        }
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                removeCallbacks(mScrollRunnable);
                if (!mOverScroller.isFinished()) {
                    mOverScroller.abortAnimation();
                }
                mLastMotionX = event.getX();
                mLastMotionY = event.getY();
                isUserScroll = true;
                isDragging = false;
                break;

            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY() - mLastMotionY;
                if (Math.abs(moveY) > mScaledTouchSlop) {
                    isDragging = true;
                }
                if (isDragging) {

//                    if (mOffset < 0) {
//                        mOffset = Math.max(mOffset, -getTextHeight(0) - mLrcLineSpaceHeight);
//                    }
                    float maxHeight = getItemOffsetY(getLrcCount() - 1);
//                    if (mOffset > maxHeight) {
//                        mOffset = Math.min(mOffset, maxHeight + getTextHeight(getLrcCount() - 1) + mLrcLineSpaceHeight);
//                    }
                    if (mOffset < 0 || mOffset > maxHeight) {
                        moveY /= 3.5f;
                    }
                    mOffset -= moveY;
                    mLastMotionY = event.getY();
                    invalidateView();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (!isDragging && (!onClickPlayButton(event))) {
                    invalidateView();
                    performClick();
                }
                handleActionUp(event);
                break;
        }
        return true;
    }

    private void handleActionUp(MotionEvent event) {
        if (overScrolled() && mOffset < 0) {
            scrollToPosition(0);
            if (isAutoAdjustPosition) {
                ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
            }
            return;
        }

        if (overScrolled() && mOffset > getItemOffsetY(getLrcCount() - 1)) {
            scrollToPosition(getLrcCount() - 1);
            if (isAutoAdjustPosition) {
                ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
            }
            return;
        }

        mVelocityTracker.computeCurrentVelocity(1000, mMaximumFlingVelocity);
        float yVelocity = mVelocityTracker.getYVelocity();
        float absYVelocity = Math.abs(yVelocity);
        if (absYVelocity > mMinimumFlingVelocity) {
            mOverScroller.fling(0, (int) mOffset, 0, (int) (-yVelocity), 0,
                    0, 0, (int) getItemOffsetY(getLrcCount() - 1),
                    0, (int) getTextHeight(0));
            invalidateView();
        }
        releaseVelocityTracker();
        if (isAutoAdjustPosition) {
            ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
        }
    }

    private boolean onClickPlayButton(MotionEvent event) {
        return false;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mOverScroller.computeScrollOffset()) {
            mOffset = mOverScroller.getCurrY();
            invalidateView();
        }
    }

    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    public void resetView(String defaultContent) {
        if (mLrcData != null) {
            mLrcData.clear();
        }
        mLrcMap.clear();
        mStaticLayoutHashMap.clear();
        mCurrentLine = 0;
        mOffset = 0;
        isUserScroll = false;
        isDragging = false;
        mDefaultContent = defaultContent;
        removeCallbacks(mScrollRunnable);
        invalidate();
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    public int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 暂停（手动滑动歌词后，不再自动回滚至当前播放位置）
     */
    public void pause() {
        isAutoAdjustPosition = false;
        invalidateView();
    }

    /**
     * 恢复（继续自动回滚）
     */
    public void resume() {
        isAutoAdjustPosition = true;
        ViewCompat.postOnAnimationDelayed(LrcView.this, mScrollRunnable, mTouchDelay);
        invalidateView();
    }

    private void invalidateView() {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            invalidate();
        } else {
            postInvalidate();
        }
    }

    public void updateLyrics(int currentPosition) {
        updateTime(currentPosition);
        invalidateView();
    }

    /**
     * 初始化歌词
     **/
    private String isUpdateLyric = null;

    public boolean initLyricString(String mPlayLyricPath) {
        String s = EncryptUtils.encryptMD5ToString(mPlayLyricPath);
        if (TextUtils.equals(s, isUpdateLyric)) {
            return isGunDongGeCi;
        }
        isUpdateLyric = s;
        isGunDongGeCi = false;
        if (!TextUtils.isEmpty(mPlayLyricPath)) {
            if (mPlayLyricPath.contains("[00:") || mPlayLyricPath.contains("[01:")) {
                isGunDongGeCi = true;
                if (mPlayLyricPath.contains("]\n[")) {
                    mPlayLyricPath = mPlayLyricPath.replace("]\n[", "][");
                }
            } else {
                mPlayLyricPath = mPlayLyricPath.replace(",", "\n");
                mPlayLyricPath = mPlayLyricPath.replace(";", "\n");
                mPlayLyricPath = mPlayLyricPath.replace(".", "\n");
                mPlayLyricPath = mPlayLyricPath.replace("!", "\n");
                mPlayLyricPath = mPlayLyricPath.replace("\\?", "\n");
                mPlayLyricPath = mPlayLyricPath.replace("，", "\n");
                mPlayLyricPath = mPlayLyricPath.replace("；", "\n");
                mPlayLyricPath = mPlayLyricPath.replace("。", "\n");
                mPlayLyricPath = mPlayLyricPath.replace("！", "\n");
                mPlayLyricPath = mPlayLyricPath.replace("？", "\n");
            }
            setLrcData(LyricsParser.parserString(mPlayLyricPath));
        } else {
            setLrcData(null);
        }
        // 解析歌词 保存到集合
        return isGunDongGeCi;
    }

}
