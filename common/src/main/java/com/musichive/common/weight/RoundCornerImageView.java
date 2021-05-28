package com.musichive.common.weight;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.appcompat.widget.AppCompatImageView;

import com.musichive.common.R;


/**
 * @author <a href="mailto:hejunqiu@everimaging.com">hejunqiu</a>
 * @version 1.0
 * @description
 * @create 18-10-29 : 下午7:47
 */
public class RoundCornerImageView extends AppCompatImageView {

    private static final int DEFAULT_MAX_VIEW_SIZE = 800;

    protected float mLeftTopRadius;
    protected float mRightTopRadius;
    protected float mLeftBottomRadius;
    protected float mRightBottomRadius;

    protected float mSelectedStrokeWidth;
    protected int mSelectedStrokeColor;

    protected int mMaxScreenSize = DEFAULT_MAX_VIEW_SIZE;

    protected int mBackgroundColor;

    private final RectF mRect = new RectF();

    private Paint backPaint;

    private Paint strokePaint;

    private final Matrix shaderMatrix = new Matrix();
    private final Matrix viewMatrix = new Matrix();

    private float mViewScale = 1.f;

    private Path renderPath = new Path();

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public RoundCornerImageView(Context context, AttributeSet attrs,
                                     int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundCornerImageView, defStyleAttr, 0);

        handleAttr(typedArray);
        initComplements();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundCornerImageView(Context context, AttributeSet attrs,
                                     int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    private void handleAttr(TypedArray typedArray) {

        float globalCornerRadius = typedArray
                .getDimension(
                        R.styleable.RoundCornerImageView_round_radius,
                        0);
        setCornerRadius(globalCornerRadius);

        if (typedArray
                .hasValue(R.styleable
                        .RoundCornerImageView_leftTop_Radius)) {
            float leftTopRadius = typedArray
                    .getDimension(
                            R.styleable
                                    .RoundCornerImageView_leftTop_Radius,
                            0);
            setLeftTopRadius(leftTopRadius);
        }

        if (typedArray
                .hasValue(R.styleable
                        .RoundCornerImageView_rightTop_Radius)) {
            float rightTopRadius = typedArray
                    .getDimension(
                            R.styleable
                                    .RoundCornerImageView_rightTop_Radius,
                            0);
            setRightTopRadius(rightTopRadius);
        }

        if (typedArray
                .hasValue(R.styleable
                        .RoundCornerImageView_leftBottom_Radius)) {
            float leftBottomRadius = typedArray
                    .getDimension(
                            R.styleable
                                    .RoundCornerImageView_leftBottom_Radius,
                            0);
            setLeftBottomRadius(leftBottomRadius);
        }

        if (typedArray
                .hasValue(R.styleable
                        .RoundCornerImageView_rightBottom_Radius)) {
            float rightBottomRadius = typedArray
                    .getDimension(
                            R.styleable
                                    .RoundCornerImageView_rightBottom_Radius,
                            0);
            setRightBottomRadius(rightBottomRadius);
        }

        float selectedStrokeWidth = typedArray
                .getDimension(
                        R.styleable
                                .RoundCornerImageView_selected_Stroke_Width,
                        0);
        setSelectedStrokeWidth(selectedStrokeWidth);

        int selectedStrokeColor = typedArray
                .getColor(
                        R.styleable
                                .RoundCornerImageView_selected_Stroke_Color,
                        0);
        setSelectedStrokeColor(selectedStrokeColor);

        int backgroundColor = typedArray.getColor(R.styleable
                .RoundCornerImageView_background_Color, 0);
        setRoundCornerBackgroundColor(backgroundColor);

        typedArray.recycle();
    }

    private void initComplements() {
        try {
            DisplayMetrics displayMetrics = getContext().getResources()
                    .getDisplayMetrics();
            int w = displayMetrics.widthPixels;
            int h = displayMetrics.heightPixels;
            mMaxScreenSize = Math.max(w, h);
        } catch (Exception e) {

        }

        backPaint = new Paint();
        backPaint.setAntiAlias(true);
        backPaint.setFilterBitmap(true);

        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setStyle(Paint.Style.STROKE);
    }

    public void setCornerRadius(float radius) {
        setLeftTopRadius(radius);
        setLeftBottomRadius(radius);
        setRightBottomRadius(radius);
        setRightTopRadius(radius);
    }

    public void setLeftTopRadius(float radius) {
        mLeftTopRadius = radius;
        if (mLeftTopRadius < 0)
            mLeftTopRadius = 0;
    }

    public void setRightTopRadius(float radius) {
        mRightTopRadius = radius;
        if (mRightTopRadius < 0)
            mRightTopRadius = 0;
    }

    public void setLeftBottomRadius(float radius) {
        mLeftBottomRadius = radius;
        if (mLeftBottomRadius < 0)
            mLeftBottomRadius = 0;
    }

    public void setRightBottomRadius(float radius) {
        mRightBottomRadius = radius;
        if (mRightBottomRadius < 0)
            mRightBottomRadius = 0;
    }

    public float getLeftTopRadius() {
        return mLeftTopRadius;
    }

    public float getRightTopRadius() {
        return mRightTopRadius;
    }

    public float getLeftBottomRadius() {
        return mLeftBottomRadius;
    }

    public float getRightBottomRadius() {
        return mRightBottomRadius;
    }

    public float getSelectedStrokeWidth() {
        return mSelectedStrokeWidth;
    }

    public void setSelectedStrokeWidth(float mSelectedStrokeWidth) {
        this.mSelectedStrokeWidth = mSelectedStrokeWidth;
        if (this.mSelectedStrokeWidth < 0) {
            this.mSelectedStrokeWidth = 0;
        }
    }

    public int getSelectedStrokeColor() {
        return mSelectedStrokeColor;
    }

    public void setSelectedStrokeColor(int mSelectedStrokeColor) {
        this.mSelectedStrokeColor = mSelectedStrokeColor;
    }

    @Deprecated
    @Override
    public void setBackground(Drawable background) {
        //do nothing
    }

    @Deprecated
    @Override
    public void setBackgroundColor(int color) {
        //do nothing
    }

    @Deprecated
    @Override
    public void setBackgroundResource(int resid) {
        //do nothing
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        //do nothing
    }

    public void setRoundCornerBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    public int getRoundCornerBackgroundColor() {
        return mBackgroundColor;
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        if (bm != null)
            setImageDrawable(new RoundedDrawable(bm));
        else {
            super.setImageBitmap(bm);
        }
    }

    private float calcViewScale(RectF viewBounds, int maxViewSize) {
        float viewScale = 1.f;
        float viewW = viewBounds.width();
        float viewH = viewBounds.height();
        if (viewW > maxViewSize || viewH > maxViewSize) {
            float viewScaleX = viewW / maxViewSize;
            float viewScaleY = viewH / maxViewSize;
            viewScale = Math.max(viewScaleX, viewScaleY);
        }
        return viewScale;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //------prevent create too large path, scale view bounds-------
        mRect.set(0, 0, w, h);

        mViewScale = calcViewScale(mRect, mMaxScreenSize);

        float drawW = mRect.width() / mViewScale;
        float drawH = mRect.height() / mViewScale;

        mRect.set(0, 0, drawW, drawH);
        viewMatrix.reset();
        viewMatrix.setScale(mViewScale, mViewScale);
        //------prevent create too large path, scale view bounds-------
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float[] outerR = new float[]{mLeftTopRadius * mViewScale,
                mLeftTopRadius * mViewScale,
                mRightTopRadius * mViewScale,
                mRightTopRadius * mViewScale,
                mRightBottomRadius * mViewScale,
                mRightBottomRadius * mViewScale,
                mLeftBottomRadius * mViewScale,
                mLeftBottomRadius * mViewScale};

        renderPath.reset();
        renderPath.addRoundRect(mRect, outerR, Path.Direction.CW);

        canvas.save();
        canvas.concat(viewMatrix);
        backPaint.setColor(mBackgroundColor);
        canvas.drawPath(renderPath, backPaint);
        canvas.restore();

        super.onDraw(canvas);


        if (mSelectedStrokeWidth > 0 && isSelected()) {
            float drawStrokeWidth = mSelectedStrokeWidth * mViewScale;
            strokePaint.setStrokeWidth(drawStrokeWidth);
            RectF insetRectF = new RectF(mRect);
            float insetOffset = drawStrokeWidth / 2.f;
            insetRectF.inset(insetOffset, insetOffset);
            Path strokePath = new Path();
            for (int i = 0; i < outerR.length; i++) {
                outerR[i] -= insetOffset;
            }
            strokePath.addRoundRect(insetRectF, outerR, Path.Direction.CW);
            canvas.save();
            canvas.concat(viewMatrix);
            canvas.drawPath(strokePath, strokePaint);
            canvas.restore();
        }
    }

    private class RoundedDrawable extends Drawable {

        protected final RectF mBitmapRect;
        protected final BitmapShader bitmapShader;
        protected final Paint paint;

        public RoundedDrawable(Bitmap bitmap) {

            bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                    Shader.TileMode.CLAMP);
            mBitmapRect = new RectF(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setFilterBitmap(true);
            paint.setShader(bitmapShader);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);

            // Resize the original bitmap to fit the new bound
            shaderMatrix.reset();
            float dwidth = mBitmapRect.width();
            float dheight = mBitmapRect.height();
            float vwidth = mRect.width();
            float vheight = mRect.height();


            ScaleType scaleType = getScaleType();
            if (ScaleType.CENTER == scaleType) {
                shaderMatrix.setTranslate(
                        (int) ((vwidth - dwidth) * 0.5f + 0.5f),
                        (int) ((vheight - dheight) * 0.5f + 0.5f));
            } else if (ScaleType.CENTER_CROP == scaleType) {
                float scale;
                float dx = 0, dy = 0;

                if (dwidth * vheight > vwidth * dheight) {
                    scale = (float) vheight / (float) dheight;
                    dx = (vwidth - dwidth * scale) * 0.5f;
                } else {
                    scale = (float) vwidth / (float) dwidth;
                    dy = (vheight - dheight * scale) * 0.5f;
                }
                shaderMatrix.setScale(scale, scale);
                shaderMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
            } else if (ScaleType.CENTER_INSIDE == scaleType) {
                float scale;
                float dx;
                float dy;

                if (dwidth <= vwidth && dheight <= vheight) {
                    scale = 1.0f;
                } else {
                    scale = Math.min((float) vwidth / (float) dwidth,
                            (float) vheight / (float) dheight);
                }

                dx = (int) ((vwidth - dwidth * scale) * 0.5f + 0.5f);
                dy = (int) ((vheight - dheight * scale) * 0.5f + 0.5f);

                shaderMatrix.setScale(scale, scale);
                shaderMatrix.postTranslate(dx, dy);
            } else {
                //:Only support ScaleType.FIT_XY now
                shaderMatrix.setRectToRect(mBitmapRect, mRect,
                        Matrix.ScaleToFit.FILL);
            }

            //fix bug: To prevent GLRender exception "Path is too large...",
            // transform canvas instead of transforming shader
            bitmapShader.setLocalMatrix(shaderMatrix);

        }

        @Override
        public void draw(Canvas canvas) {

            canvas.save();
            canvas.concat(viewMatrix);
            canvas.drawPath(renderPath, paint);
            canvas.restore();

        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            paint.setColorFilter(cf);
        }
    }
}
