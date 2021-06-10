package com.musichive.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IntRange;
import androidx.annotation.IntegerRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.musichive.common.R;

import java.security.MessageDigest;

/**
 * @Author Jun
 * Date 2021 年 01月 28 日 11:22
 * Description android_client
 */
public class BlurUtil {

    private static RequestOptions requestOptions = null;
    private static RequestOptions requestOptionsBlur = null;
    private static RequestOptions requestOptionsBlur2 = null;

    // 乐库玻璃球效果
    public static void postsGlassBall(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        if (requestOptions == null) {
            requestOptions = new RequestOptions()
                    .transform(new PostsGlassBallBlurTransformation((int) SizeUtils.dp2px(8), 90))//90模糊度
                    .placeholder(R.drawable.shape_groups_post_play_num)
                    .error(R.drawable.shape_groups_post_play_num);
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(imageView);
    }

    // 乐库玻璃球效果
    public static void postsGlassBall2(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        if (requestOptions == null) {
            requestOptions = new RequestOptions()
                    .transform(new PostsGlassBallBlurTransformation((int) SizeUtils.dp2px(8), 90))//90模糊度
                    .placeholder(R.drawable.shape_groups_post_play_num_shiji)
                    .error(R.drawable.shape_groups_post_play_num_shiji);
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(requestOptions)
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(imageView);
    }

    // 加载一个图片模糊到imageview上面
    public static void blurImageView(Context context, String url, ImageView imageView) {
        if (imageView == null||context==null) {
            return;
        }
        if (requestOptionsBlur == null) {
            requestOptionsBlur = new RequestOptions().
                    error(R.drawable.default_pic).
                    transform(new ABlurTransformation(40, 40, CORNER_ALL, false));//90模糊度
        }
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(requestOptionsBlur)
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static void blurImageViewRectangle(Context context, String url, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        RequestOptions requestOptionsBlur = new RequestOptions()
                .error(R.drawable.default_pic)
                .transform(new ABlurTransformation(40, 50, CORNER_TOP, true));//90模糊度
        Glide.with(context)
                .asBitmap()
                .load(url)
                .apply(requestOptionsBlur)
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static void blurImageViewResources(Context context, @IntegerRes int src, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        RequestOptions requestOptionsBlur = new RequestOptions()
                .error(R.drawable.default_pic)
                .transform(new ABlurTransformation(40, 50, CORNER_ALL, true));//90模糊度
        Glide.with(context)
                .asBitmap()
                .load(src)
                .apply(requestOptionsBlur)
                .transition(BitmapTransitionOptions.withCrossFade())
                .into(imageView);
    }

    // 加载一个图片模糊到view背景上面
    public static void blurViewBackGround(Context context, Object url, View view,int blur,int errid) {
        if (view == null||url==null) {
            return;
        }
        RequestOptions  requestOptionsBlur2 = new RequestOptions().centerCrop().
                    error(errid).
                    transform(new ABlurTransformation(0, blur, CORNER_NONE, true));
        Glide.with(context)
                .asDrawable()
                .load(url)
                .apply(requestOptionsBlur2)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        view.setBackground(resource);
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        view.setBackground(errorDrawable);
                    }
                });
    }

    // 加载一个图片模糊到view背景上面
    public static void blurTextViewBackGround(Context context, Object url, View view) {
        if (view == null||url==null) {
            return;
        }
        RequestOptions requestOptionsBlur = new RequestOptions()
                .error(R.drawable.default_pic)
                .transform(new ABlurTransformation(8, 50, CORNER_ALL, true));//90模糊度
        Glide.with(context)
                .asDrawable()
                .load(url)
                .apply(requestOptionsBlur)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        view.getMeasuredWidth();
                        view.setBackground(resource);

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        view.setBackground(errorDrawable);
                    }
                });
    }

    public static void clearVariable() {
        requestOptionsBlur = null;
        requestOptionsBlur2 = null;
        requestOptions = null;
    }

    //图片模糊化处理
    public static Bitmap blurBitmap(Bitmap bitmap, Context context, float radius) {
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        blurScript.setRadius(radius);
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        allOut.copyTo(outBitmap);
        bitmap.recycle();
        rs.destroy();
        return outBitmap;
    }

    public static final int CORNER_NONE = 0;
    public static final int CORNER_TOP_LEFT = 1;
    public static final int CORNER_TOP_RIGHT = 1 << 1;
    public static final int CORNER_BOTTOM_LEFT = 1 << 2;
    public static final int CORNER_BOTTOM_RIGHT = 1 << 3;
    public static final int CORNER_ALL = CORNER_TOP_LEFT | CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
    public static final int CORNER_TOP = CORNER_TOP_LEFT | CORNER_TOP_RIGHT;
    public static final int CORNER_BOTTOM = CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT;
    public static final int CORNER_LEFT = CORNER_TOP_LEFT | CORNER_BOTTOM_LEFT;
    public static final int CORNER_RIGHT = CORNER_TOP_RIGHT | CORNER_BOTTOM_RIGHT;

    public static Bitmap fillet(Bitmap bitmap, int roundPx, int corners) {
        try {
            // 其原理就是：先建立一个与图片大小相同的透明的Bitmap画板
            // 然后在画板上画出一个想要的形状的区域。
            // 最后把源图片帖上。
            final int width = bitmap.getWidth();
            final int height = bitmap.getHeight();

            Bitmap paintingBoard = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(paintingBoard);
            canvas.drawARGB(Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT, Color.TRANSPARENT);

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);

            //画出4个圆角
            final RectF rectF = new RectF(0, 0, width, height);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            //把不需要的圆角去掉
            int notRoundedCorners = corners ^ CORNER_ALL;
            if ((notRoundedCorners & CORNER_TOP_LEFT) != 0) {
                clipTopLeft(canvas, paint, roundPx, width, height);
            }
            if ((notRoundedCorners & CORNER_TOP_RIGHT) != 0) {
                clipTopRight(canvas, paint, roundPx, width, height);
            }
            if ((notRoundedCorners & CORNER_BOTTOM_LEFT) != 0) {
                clipBottomLeft(canvas, paint, roundPx, width, height);
            }
            if ((notRoundedCorners & CORNER_BOTTOM_RIGHT) != 0) {
                clipBottomRight(canvas, paint, roundPx, width, height);
            }
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

            //帖子图
            final Rect src = new Rect(0, 0, width, height);
            final Rect dst = src;
            canvas.drawBitmap(bitmap, src, dst, paint);
            return paintingBoard;
        } catch (Exception exp) {
            return bitmap;
        }
    }

    private static void clipTopLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(0, 0, offset, offset);
        canvas.drawRect(block, paint);
    }

    private static void clipTopRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(width - offset, 0, width, offset);
        canvas.drawRect(block, paint);
    }

    private static void clipBottomLeft(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(0, height - offset, offset, height);
        canvas.drawRect(block, paint);
    }

    private static void clipBottomRight(final Canvas canvas, final Paint paint, int offset, int width, int height) {
        final Rect block = new Rect(width - offset, height - offset, width, height);
        canvas.drawRect(block, paint);
    }


    public static class PostsGlassBallBlurTransformation extends BitmapTransformation {
        private static final String ID = PostsGlassBallBlurTransformation.class.getName();
        private static final byte[] ID_BYTES = ID.getBytes(Key.CHARSET);
        public static final int DEFAULT_RADIUS = 15;
        private int mRadius = DEFAULT_RADIUS;
        private int mBlurRadius = DEFAULT_RADIUS;

        public PostsGlassBallBlurTransformation(@IntRange(from = 0) int radius, @IntRange(from = 0) int radiusBlur) {
            mRadius = radius;
            mBlurRadius = radiusBlur;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(ID_BYTES);
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmapCrop = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
            Bitmap bitmap = fillet(bitmapCrop, mRadius, CORNER_TOP_RIGHT | CORNER_BOTTOM_LEFT | CORNER_BOTTOM_RIGHT);
            return FastBlur.doBlur(bitmap, mBlurRadius, true);
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof PostsGlassBallBlurTransformation;
        }

        @Override
        public int hashCode() {
            return ID.hashCode();
        }
    }

    public static class ABlurTransformation extends BitmapTransformation {
        private static final String ID = ABlurTransformation.class.getName();
        private static final byte[] ID_BYTES = ID.getBytes(Key.CHARSET);
        public static final int DEFAULT_RADIUS = 15;
        private int mRadius = DEFAULT_RADIUS;
        private int mBlurRadius = DEFAULT_RADIUS;
        int corners;
        boolean isBitmapCrop;

        public ABlurTransformation(@IntRange(from = 0) int radius, @IntRange(from = 0) int radiusBlur, int corners, boolean isBitmapCrop) {
            mRadius = radius;
            mBlurRadius = radiusBlur;
            this.corners = corners;
            this.isBitmapCrop = isBitmapCrop;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(ID_BYTES);
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap;
            if (isBitmapCrop) {
                bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
            } else {
                bitmap = fillet(toTransform, mRadius, corners);
            }
            //先模糊 再加 裁剪
            bitmap = FastBlur.doBlur(bitmap, mBlurRadius, true);
            bitmap = fillet(bitmap, mRadius, corners);
            return bitmap;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof ABlurTransformation;
        }

        @Override
        public int hashCode() {
            return ID.hashCode();
        }
    }

}
