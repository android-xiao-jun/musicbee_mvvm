package com.musichive.main.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;

import com.musichive.main.R;


/**
 * @author <a href="mailto:hejunqiu@everimaging.com">hejunqiu</a>
 * @version 1.0
 * @description
 * @create 18-10-24 : 下午5:37
 */
public class AvatarImageView extends CircleImageView {

    private int mDefaultAvatar;

    public AvatarImageView(Context context) {
        this(context, null);
    }

    public AvatarImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageView, defStyle, defStyle);
        int resId = typeArray.getResourceId(R.styleable.AvatarImageView_defaultAvatar,
                R.drawable.default_head);
        mDefaultAvatar = resId;
        typeArray.recycle();
        setImageResource(mDefaultAvatar);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}

