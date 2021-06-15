package com.musichive.main.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.musichive.main.R;

import java.math.BigDecimal;


/**
 * @Author Jun
 * Date 2021 年 06月 15 日 11:38
 * Description 音乐蜜蜂-mvvm版本
 */
public class PlayerSeekBar extends View {
    private static final String TAG = PlayerSeekBar.class.getSimpleName();
    private RectF mTickBarRecf;

    /**
     * 默认的刻度条paint
     */
    private Paint mTickBarPaint;
    /**
     * 默认刻度条的高度
     */
    private float mTickBarHeight;
    /**
     * 默认刻度条的颜色
     */
    private int mTickBarColor;
    //------------------
    /**
     * 圆形按钮paint
     */
    private Paint mCircleButtonPaint;
    /**
     * 圆形按钮颜色
     */
    private int mCircleButtonColor;
    /**
     * 圆形按钮文本颜色
     */
    private int mCircleButtonTextColor;
    /**
     * 圆形按钮文本大小
     */
    private float mCircleButtonTextSize;
    /**
     * 圆形按钮的半径
     */
    private float mCircleButtonRadius;
    /**
     * 圆形按钮的recf，矩形区域
     */
    private RectF mCircleRecf;
    /**
     * 圆形按钮button文本绘制paint
     */
    private Paint mCircleButtonTextPaint;
    private Drawable mCircleDrawable;
    private Drawable mDefaultDrawable;


    //----------------------
    /**
     * 进度条高度大小
     */
    private float mProgressHeight;
    /**
     * 进度条paint
     */
    private Paint mProgressPaint;
    private Drawable mProgressDrawable;
    /**
     * 进度颜色
     */
    private int mProgressColor;

    /**
     * 进度条的recf，矩形区域
     */
    private RectF mProgressRecf;
    /**
     * 选中的进度值
     */
    private int mSelectProgress;
    /**
     * 进度最大值
     */
    private int mMaxProgress = DEFAULT_MAX_VALUE;
    /**
     * 默认的最大值
     */
    private static final int DEFAULT_MAX_VALUE = 10;


    /**
     * view的总进度宽度，除去paddingtop以及bottom
     */
    private int mViewWidth;
    /**
     * view的总进度高度，除去paddingtop以及bottom
     */
    private int mViewHeight;
    /**
     * 圆形button的圆心坐标，也是progress进度条的最右边的的坐标
     */
    private float mCirclePotionX;
    /**
     * 是否显示圆形按钮的文本
     */
    private boolean mIsShowButtonText;
    /**
     * 是否显示圆形按钮
     */
    private boolean mIsShowButton;
    /**
     * 是否显示圆角
     */
    private boolean mIsRound;
    /**
     * 起始的进度值，比如从1开始显示
     */
    private int mStartProgress;
    /**
     * 圆形按钮外边的光圈
     */
    private int mCircleApertureWidth;
    /**
     * 圆形按钮光圈的颜色
     */
    private int mCircleApertureColor;
    /**
     * 圆形按钮光圈的paint
     */
    private Paint mCircleAperturePaint;
    /**
     * 是否显示圆形光圈
     */
    private boolean mIsShowCircleAperture;
    /**
     * view边框的描边paint
     */
    private Paint mBorderPaint;
    /**
     * 边框线粗细，dp
     */
    private float mBorderSize;
    /**
     * 边框线颜色
     */
    private int mBorderColor;
    private RectF mBorderRecf;
    private int mHeight;
    private int mWidth;
    private RectF mCircleApertureRectF;

    /**
     * 监听进度条变化
     */
    public interface OnProgressChangeListener {
        void onChange(int selectProgress, boolean formUser);
    }

    /**
     * 监听进度条变化
     */
    private OnProgressChangeListener mOnProgressChangeListener;

    /**
     * 设置进度条变化的监听器，当触摸停止时触发
     *
     * @param onProgressChangeListener 进度条变化的监听器
     */
    public void setOnProgressChangeListener(OnProgressChangeListener onProgressChangeListener) {
        mOnProgressChangeListener = onProgressChangeListener;
    }

    public PlayerSeekBar(Context context) {
        this(context, null);
    }

    public PlayerSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }


    /**
     * 初始化view的属性
     *
     * @param context 上下文
     * @param attrs   attr属性
     */
    private void init(Context context, AttributeSet attrs) {

        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PlayerSeekBar);
        mTickBarHeight = attr.getDimensionPixelOffset(R.styleable
                .PlayerSeekBar_PlayerSeekBar_tickBarHeight, getDpValue(8));
        mTickBarColor = attr.getColor(R.styleable.PlayerSeekBar_PlayerSeekBar_tickBarColor, Color.parseColor("#F6F6F6"));
        mCircleButtonColor = attr.getColor(R.styleable
                .PlayerSeekBar_PlayerSeekBar_circleButtonColor, Color.parseColor("#FFFFFF"));
        mCircleButtonTextColor = attr.getColor(R.styleable
                .PlayerSeekBar_PlayerSeekBar_circleButtonTextColor, Color.parseColor("#828282"));
        mCircleButtonTextSize = attr.getDimension(R.styleable
                .PlayerSeekBar_PlayerSeekBar_circleButtonTextSize, 0);
        mCircleButtonRadius = attr.getDimensionPixelOffset(R.styleable
                .PlayerSeekBar_PlayerSeekBar_circleButtonRadius, 0);
        mCircleApertureWidth = attr.getDimensionPixelOffset(R.styleable
                .PlayerSeekBar_PlayerSeekBar_circleApertureWidth, 0);
        mCircleApertureColor = attr.getColor(R.styleable
                .PlayerSeekBar_PlayerSeekBar_circleApertureColor, Color.parseColor("#1A1A1A"));

        mProgressHeight = attr.getDimensionPixelOffset(R.styleable
                .PlayerSeekBar_PlayerSeekBar_progressHeight, 0);
        mProgressColor = attr.getColor(R.styleable.PlayerSeekBar_PlayerSeekBar_progressColor, Color.parseColor("#FFFFFF"));
        mSelectProgress = attr.getInt(R.styleable.PlayerSeekBar_PlayerSeekBar_selectProgress, 0);
        mStartProgress = attr.getInt(R.styleable.PlayerSeekBar_PlayerSeekBar_startProgress, 0);
        mMaxProgress = attr.getInt(R.styleable.PlayerSeekBar_PlayerSeekBar_maxProgress, 10);
        mIsShowButtonText = attr.getBoolean(R.styleable
                .PlayerSeekBar_PlayerSeekBar_isShowButtonText, false);
        mIsShowButton = attr.getBoolean(R.styleable.PlayerSeekBar_PlayerSeekBar_isShowButton,
                false);
        mIsRound = attr.getBoolean(R.styleable.PlayerSeekBar_PlayerSeekBar_isRound, false);
        mBorderSize = attr.getDimensionPixelOffset(R.styleable
                .PlayerSeekBar_PlayerSeekBar_borderSize, 0);
        mBorderColor = attr.getColor(R.styleable.PlayerSeekBar_PlayerSeekBar_borderColor, Color.parseColor("#FFFFFF"));

        if (attr.hasValue(R.styleable.PlayerSeekBar_PlayerSeekBar_progressDrawable)) {
            int resourceId = attr.getResourceId(R.styleable.PlayerSeekBar_PlayerSeekBar_progressDrawable, 0);
            mProgressDrawable = ContextCompat.getDrawable(context, resourceId);
        }
        if (attr.hasValue(R.styleable.PlayerSeekBar_PlayerSeekBar_circleDrawable)) {
            int resourceId = attr.getResourceId(R.styleable.PlayerSeekBar_PlayerSeekBar_circleDrawable, 0);
            mCircleDrawable = ContextCompat.getDrawable(context, resourceId);
        }
        if (attr.hasValue(R.styleable.PlayerSeekBar_PlayerSeekBar_progressDefaultDrawable)) {
            int resourceId = attr.getResourceId(R.styleable.PlayerSeekBar_PlayerSeekBar_progressDefaultDrawable, 0);
            mDefaultDrawable = ContextCompat.getDrawable(context, resourceId);
        }
        initView();

        attr.recycle();


    }

    private void initView() {
        mBorderPaint = new Paint();
        mBorderPaint.setStrokeWidth(mBorderSize);
        mBorderPaint.setColor(mBorderColor);
        mBorderPaint.setStyle(Paint.Style.FILL);
        mBorderPaint.setAntiAlias(true);

        mProgressPaint = new Paint();
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setAntiAlias(true);

        mCircleButtonPaint = new Paint();
        mCircleButtonPaint.setColor(mCircleButtonColor);
        mCircleButtonPaint.setStyle(Paint.Style.FILL);
        mCircleButtonPaint.setAntiAlias(true);

        mCircleAperturePaint = new Paint();
//        mCircleAperturePaint.setStrokeWidth(mCircleApertureWidth);
        mCircleAperturePaint.setColor(mCircleApertureColor);
        mCircleAperturePaint.setStyle(Paint.Style.FILL);
        mCircleAperturePaint.setAntiAlias(true);

        mCircleButtonTextPaint = new Paint();
        mCircleButtonTextPaint.setTextAlign(Paint.Align.CENTER);
        mCircleButtonTextPaint.setColor(mCircleButtonTextColor);
        mCircleButtonTextPaint.setStyle(Paint.Style.FILL);
        mCircleButtonTextPaint.setTextSize(mCircleButtonTextSize);
        mCircleButtonTextPaint.setAntiAlias(true);

        mTickBarPaint = new Paint();
        mTickBarPaint.setColor(mTickBarColor);
        mTickBarPaint.setStyle(Paint.Style.FILL);
        mTickBarPaint.setAntiAlias(true);

        mTickBarRecf = new RectF();
        mProgressRecf = new RectF();
        mCircleRecf = new RectF();
        mBorderRecf = new RectF();
        mCircleApertureRectF = new RectF();

        setCircleApertureWidth(mCircleApertureWidth);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            //如果设置不可用，则禁用触摸设置进度
            return false;
        }
        getParent().requestDisallowInterceptTouchEvent(true);
        float x = event.getX();
        float y = event.getY();
//        Log.i(TAG, "onTouchEvent: x：" + x);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                judgePosition(x);
                return true;
            case MotionEvent.ACTION_DOWN:
                judgePosition(x);
                return true;
            case MotionEvent.ACTION_UP:
                if (mOnProgressChangeListener != null) {
                    Log.i(TAG, "onTouchEvent: 触摸结束，通知监听器-mSelectProgress：" + mSelectProgress);
                    mOnProgressChangeListener.onChange(mSelectProgress, true);
                }
                return true;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    private void judgePosition(float x) {
        float end = getPaddingLeft() + mViewWidth;
        float start = getPaddingLeft();
        int progress = mSelectProgress;
        if (x >= start) {
            double result = (x - start) / mViewWidth * (float) mMaxProgress;
            BigDecimal bigDecimal = new BigDecimal(result).setScale(0, BigDecimal.ROUND_HALF_UP);
            progress = bigDecimal.intValue();
            if (progress > mMaxProgress) {
//                Log.i(TAG, "judgePosition:x > end  超出坐标范围:");
                progress = mMaxProgress;
            }
        } else if (x < start) {
//            Log.i(TAG, "judgePosition: x < start 超出坐标范围:");
            progress = 0;
        }
        if (progress != mSelectProgress) {
            //发生变化才通知view重新绘制
            setSelectProgress(progress, true);
        }

    }

    private int getMySize(int measureSpec, int defaultSize) {

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int mySize = defaultSize;

        switch (mode) {
            case MeasureSpec.UNSPECIFIED: {//如果没有指定大小，就设置为默认大小
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {//如果测量模式是最大取值为size，wrap_content
                //我们将大小取最大值,你也可以取其他值
                mySize = (int) (defaultSize + mBorderSize * 2);
                break;
            }
            case MeasureSpec.EXACTLY: {//如果是固定的大小，和默认大小比较，取最大值。match_parent or 固定大小
                mySize = size < defaultSize ? defaultSize : size;
                Log.i(TAG, "getMySize: defaultsize:" + defaultSize + "  size:" + size);
                break;
            }
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int width = getMySize(widthMeasureSpec, 0);
        float defaultHeight = mTickBarHeight;

        if (mCircleApertureWidth > 0) {
            if (defaultHeight < mCircleButtonRadius * 2 + mCircleApertureWidth * 2) {
                defaultHeight = mCircleButtonRadius * 2 + mCircleApertureWidth * 2;
                Log.i(TAG, "onMeasure: defaultHeight 1 :" + defaultHeight);
            }
        } else {
            if (defaultHeight < mCircleButtonRadius * 2) {
                defaultHeight = mCircleButtonRadius * 2;
                Log.i(TAG, "onMeasure: defaultHeight 2 :" + defaultHeight);

            }
        }
        if (defaultHeight < mProgressHeight) {
            defaultHeight = mProgressHeight;
            Log.i(TAG, "onMeasure: defaultHeight 3 :" + defaultHeight);

        }
        int height = getMySize(heightMeasureSpec, (int) defaultHeight);
        Log.i(TAG, "onMeasure: width:" + width + "  height:" + height);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initValues(mWidth, mHeight);
        if (mIsRound) {
            //绘制边框
            if (mBorderSize > 0) {
                //显示绘制边框，通过后面绘制的图像重叠，只露出边框部分。这里取巧绘制边框线。
                canvas.drawRoundRect(mBorderRecf, mTickBarHeight / 2 + mBorderSize, mHeight / 2 +
                                mBorderSize,
                        mBorderPaint);
            }
            if (mDefaultDrawable != null) {
                mDefaultDrawable.setBounds((int) mTickBarRecf.left, (int) mTickBarRecf.top, (int) mTickBarRecf.right, (int) mTickBarRecf.bottom);
                mDefaultDrawable.draw(canvas);
            } else {
                canvas.drawRoundRect(mTickBarRecf, mTickBarHeight / 2, mTickBarHeight / 2, mTickBarPaint);//绘制背景刻度
            }

            if (mSelectProgress > mStartProgress) {
                if (mProgressDrawable != null) {
                    mProgressDrawable.setBounds((int) mProgressRecf.left, (int) mProgressRecf.top, (int) mProgressRecf.right, (int) mProgressRecf.bottom);
                    mProgressDrawable.draw(canvas);
                } else {
                    int saveCount = canvas.saveLayer(mProgressRecf, mProgressPaint, Canvas.ALL_SAVE_FLAG);
                    canvas.drawRoundRect(mTickBarRecf, mProgressHeight / 2, mProgressHeight / 2,
                            mProgressPaint);//这里用画进度的画笔又绘制一个背景刻度，一会要做图形异或处理，获取进度的最终形状
                    mProgressPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
                    //叠加异或形状
                    canvas.drawRect(mProgressRecf, mProgressPaint);
                    mProgressPaint.setXfermode(null);
                    canvas.restoreToCount(saveCount);//保存，这样后面的绘制不会受到影响
                }
            }
        } else {
            //绘制边框
            if (mBorderSize > 0) {
                canvas.drawRect(mBorderRecf, mBorderPaint);
            }
            canvas.drawRect(mTickBarRecf, mTickBarPaint);
            if (mSelectProgress > mStartProgress) {
                canvas.drawRect(mProgressRecf, mProgressPaint);
            }
        }
        if (mIsShowButton) {
            if (mIsShowCircleAperture) {
//                if (mViewHeight / 2 < mCircleButtonRadius + mCircleApertureWidth) {
//                    mCircleApertureWidth = (int) (mViewHeight / 2 - mCircleButtonRadius);
//                }
                canvas.drawCircle(mCirclePotionX, mHeight / 2,
                        mCircleButtonRadius +
                                mCircleApertureWidth, mCircleAperturePaint);
                Log.i(TAG, "onDraw: 显示圆形按钮光晕效果");
//                canvas.drawArc(mCircleApertureRectF, 0, 360, false, mCircleAperturePaint);
            }
            if (mCircleDrawable != null) {
                mCircleDrawable.setBounds((int) (mCirclePotionX - mCircleButtonRadius)
                        , (int) (mHeight / 2 - mCircleButtonRadius)
                        , (int) (mCirclePotionX + mCircleButtonRadius)
                        , (int) (mHeight / 2 + mCircleButtonRadius)
                );
                mCircleDrawable.draw(canvas);
            } else {
                canvas.drawCircle(mCirclePotionX, mHeight / 2, mCircleButtonRadius,
                        mCircleButtonPaint);
            }

        }
        if (mIsShowButtonText) {
            Paint.FontMetricsInt fontMetrics = mCircleButtonTextPaint.getFontMetricsInt();
            int baseline = (int) ((mCircleRecf.bottom + mCircleRecf.top - fontMetrics.bottom -
                    fontMetrics
                            .top) / 2);
            // 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()
            canvas.drawText(String.valueOf(mSelectProgress), mCircleRecf.centerX
                            (), baseline,
                    mCircleButtonTextPaint);

        }
    }

    private void initValues(int width, int height) {
        //view内容区域的高宽，不含边框。不是整体view的高宽
        mViewWidth = (int) (width - mBorderSize * 2);
        mViewHeight = (int) (height - mBorderSize * 2);

        int leftPadding = (int) mBorderSize;
        int topPadding = (int) mBorderSize;

        if (mTickBarHeight > mViewHeight) {
            //如果刻度条的高度大于view本身的高度的1/2，则显示不完整，所以处理下。
            mTickBarHeight = mViewHeight;
        }
        mTickBarRecf.set(leftPadding, (mViewHeight - mTickBarHeight) /
                2 + topPadding, mViewWidth + leftPadding, mTickBarHeight / 2 +
                mViewHeight / 2 + topPadding);
        mBorderRecf.set(mTickBarRecf.left - mBorderSize, mTickBarRecf.top - mBorderSize,
                mTickBarRecf
                        .right + mBorderSize, mTickBarRecf.bottom + mBorderSize); //边框是紧贴边缘绘制

        mCirclePotionX = (float) (mSelectProgress - mStartProgress) /
                (mMaxProgress - mStartProgress) * mViewWidth + leftPadding;//原型按按钮圆心点，也是进度条的右结束点

        if (mProgressHeight > mViewHeight) {
            //如果刻度条的高度大于view本身的高度的1/2，则显示不完整，所以处理下。
            mProgressHeight = mViewHeight;
        }

        mProgressRecf.set(leftPadding, (mViewHeight - mProgressHeight) / 2 + topPadding,
                mCirclePotionX, mProgressHeight / 2 + mViewHeight / 2 + topPadding);

        if (mCircleButtonRadius > mViewHeight / 2) {
            //如果圆形按钮的半径大于view本身的高度的1/2，则显示不完整，所以处理下。
            mCircleButtonRadius = mViewHeight / 2;
        }
        mCircleRecf.set(mCirclePotionX - mCircleButtonRadius, mViewHeight / 2 -
                        mCircleButtonRadius + topPadding,
                mCirclePotionX + mCircleButtonRadius, mViewHeight / 2 +
                        mCircleButtonRadius + topPadding);
        mCircleApertureRectF.set(mCircleRecf.left - mCircleApertureWidth, mCircleRecf.top -
                mCircleApertureWidth, mCircleRecf.right + mCircleApertureWidth, mCircleRecf.bottom +
                mCircleApertureWidth);
    }

    /**
     * seekbar背后的刻度条高度
     *
     * @return seekbar背后的刻度条高度
     */
    public float getTickBarHeight() {
        return mTickBarHeight;
    }

    /**
     * 设置seekbar背后的刻度条高度
     *
     * @param tickBarHeight seekbar背后的刻度条高度
     */
    public void setTickBarHeight(float tickBarHeight) {
        mTickBarHeight = tickBarHeight;
    }

    /**
     * seekbar背后的刻度条颜色
     *
     * @return seekbar背后的刻度条颜色
     */
    public int getTickBarColor() {
        return mTickBarColor;
    }

    /**
     * 设置seekbar背后的刻度条颜色
     *
     * @param tickBarColor seekbar背后的刻度条颜色
     */
    public void setTickBarColor(int tickBarColor) {
        mTickBarColor = tickBarColor;
        mTickBarPaint.setColor(mTickBarColor);
    }

    /**
     * 圆形按钮颜色
     *
     * @return 圆形按钮颜色
     */
    public int getCircleButtonColor() {
        return mCircleButtonColor;
    }

    /**
     * 设置圆形按钮颜色
     *
     * @param circleButtonColor 圆形按钮颜色
     */
    public void setCircleButtonColor(int circleButtonColor) {
        mCircleButtonColor = circleButtonColor;
        mCircleButtonPaint.setColor(mCircleButtonColor);
    }

    /**
     * 圆形按钮文本颜色
     *
     * @return 圆形按钮文本颜色
     */
    public int getCircleButtonTextColor() {
        return mCircleButtonTextColor;
    }

    /**
     * 设置圆形按钮文本颜色
     *
     * @param circleButtonTextColor 圆形按钮文本颜色
     */
    public void setCircleButtonTextColor(int circleButtonTextColor) {
        mCircleButtonTextColor = circleButtonTextColor;
        mCircleButtonTextPaint.setColor(mCircleButtonTextColor);
    }

    /**
     * 圆形按钮文本字体大小
     *
     * @return 圆形按钮文本字体大小
     */
    public float getCircleButtonTextSize() {
        return mCircleButtonTextSize;
    }

    /**
     * 设置圆形按钮文本字体大小
     *
     * @param circleButtonTextSize 圆形按钮文本字体大小
     */
    public void setCircleButtonTextSize(float circleButtonTextSize) {
        mCircleButtonTextSize = circleButtonTextSize;
        mCircleButtonTextPaint.setTextSize(mCircleButtonTextSize);
    }

    /**
     * 圆形按钮半径
     *
     * @return 圆形按钮半径
     */
    public float getCircleButtonRadius() {
        return mCircleButtonRadius;
    }

    /**
     * 设置圆形按钮半径
     *
     * @param circleButtonRadius 圆形按钮半径
     */
    public void setCircleButtonRadius(float circleButtonRadius) {
        mCircleButtonRadius = circleButtonRadius;
    }

    /**
     * 进度条高度
     *
     * @return 进度条高度
     */
    public float getProgressHeight() {
        return mProgressHeight;
    }

    /**
     * 设置进度条高度
     *
     * @param progressHeight 进度条高度
     */
    public void setProgressHeight(float progressHeight) {
        mProgressHeight = progressHeight;
    }

    /**
     * 进度条颜色
     *
     * @return 进度条颜色
     */
    public int getProgressColor() {
        return mProgressColor;
    }

    /**
     * 设置进度条颜色
     *
     * @param progressColor 进度条颜色
     */
    public void setProgressColor(int progressColor) {
        mProgressColor = progressColor;
        mProgressPaint.setColor(mProgressColor);
    }

    /**
     * 最大进度条的值
     *
     * @return 最大进度条的值
     */
    public int getMaxProgress() {
        return mMaxProgress;
    }

    /**
     * 设置最大进度条的值
     *
     * @param maxProgress 最大进度条的值
     */
    public void setMaxProgress(int maxProgress) {
        mMaxProgress = maxProgress;
    }

    /**
     * 设置当前选中的值
     *
     * @param selectProgress 进度
     */
    public void setSelectProgress(int selectProgress) {
        this.setSelectProgress(selectProgress, false);
    }

    /**
     * 设置当前选中的值
     *
     * @param selectProgress   进度
     * @param isNotifyListener 是否通知progresschangelistener
     */
    public void setSelectProgress(int selectProgress, boolean isNotifyListener) {
        getSelectProgressValue(selectProgress);
        Log.i(TAG, "mSelectProgress: " + mSelectProgress + "  mMaxProgress: " +
                mMaxProgress);
        if (mOnProgressChangeListener != null && isNotifyListener) {
            mOnProgressChangeListener.onChange(mSelectProgress, false);
        }
        invalidate();
    }


    /**
     * 计算当前选中的进度条的值
     *
     * @param selectProgress 进度
     */
    private void getSelectProgressValue(int selectProgress) {
        mSelectProgress = selectProgress;
        if (mSelectProgress > mMaxProgress) {
            mSelectProgress = mMaxProgress;
        } else if (mSelectProgress <= mStartProgress) {
            mSelectProgress = mStartProgress;
        }
    }


    /**
     * 获取当前的选中值
     *
     * @return 当前的选中值
     */
    public int getSelectProgress() {
        return mSelectProgress;
    }

    /**
     * 起始的刻度值
     *
     * @return 起始的刻度值
     */
    public int getStartProgress() {
        return mStartProgress;
    }

    /**
     * 设置起始刻度值
     *
     * @param startProgress 起始刻度值
     */
    public void setStartProgress(int startProgress) {
        mStartProgress = startProgress;
    }

    /**
     * 设置圆形光圈的宽度，即圆形按钮之外的延伸宽度.超出view高度，自动适配最大值。
     *
     * @param circleApertureWidth 宽度,dp
     */
    public void setCircleApertureWidth(int circleApertureWidth) {
//        mCircleApertureWidth = getDpValue(circleApertureWidth);
        mIsShowCircleAperture = circleApertureWidth > 0;
    }

    /**
     * 圆形光圈颜色
     *
     * @param circleApertureColor 圆形光圈颜色
     */
    public void setCircleApertureColor(int circleApertureColor) {
        mCircleApertureColor = circleApertureColor;
        mCircleAperturePaint.setColor(mCircleApertureColor);
    }


    /**
     * 获取dp对应的px值
     *
     * @param value 要转换的值
     * @return dp对应的px值
     */
    private int getDpValue(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getContext()
                .getResources().getDisplayMetrics());
    }
}
