package com.musichive.common.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.musichive.common.R;

/**
 * @author Jun
 * @description 用于显示身份标识 v （企业认证（蓝v）还是个人实名认证（黄v））
 * @create 2021年3月15日09:57:52
 */
public class AvatarImageTagView extends ConstraintLayout {
    public static final int TAG_DEFAULT = 0;
    public static final int TAG_PERSONAL = 1;
    public static final int TAG_ENTERPRISE = 2;

    private static final int DEFAULT_BORDER_WIDTH = 0;
    private static final int DEFAULT_BORDER_COLOR = Color.BLACK;

    //当前身份标志
    private int mCurrTag = TAG_DEFAULT;
    private int mDefaultAvatar;

    //是否需要边框
    private int mBorderColor = DEFAULT_BORDER_COLOR;
    private int mBorderWidth = DEFAULT_BORDER_WIDTH;

    //真实图片view（圆形）
    private AvatarImageView mAvatarImageView;
    //正常图（原本图片）
    private ImageView mAvatarnormal;
    //当前身份标签view
    private ImageView ivTag;
    //默认配置
    private static final RequestOptions mOptions = new RequestOptions()
            .placeholder(R.drawable.default_head)
            .error(R.drawable.default_head);

    public AvatarImageTagView(Context context) {
        this(context, null);
    }

    public AvatarImageTagView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AvatarImageTagView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.AvatarImageTagView, defStyle, defStyle);
        mDefaultAvatar = typeArray.getResourceId(R.styleable.AvatarImageTagView_defaultAvatarTag, R.drawable.default_head);
        mCurrTag = typeArray.getInt(R.styleable.AvatarImageTagView_aiTag, TAG_DEFAULT);
        mBorderWidth = typeArray.getDimensionPixelSize(R.styleable.AvatarImageTagView_fotor_CI_border_width_tag, DEFAULT_BORDER_WIDTH);
        mBorderColor = typeArray.getColor(R.styleable.AvatarImageTagView_fotor_CI_border_color_tag, DEFAULT_BORDER_COLOR);
//        final int index = typeArray.getInt(R.styleable.ImageView_scaleType, -1);
//        if (index >= 0) {
//            setScaleType(sScaleTypeArray[index]);
//        }
        typeArray.recycle();

        LayoutInflater.from(context).inflate(R.layout.view_image_tag_avatar, this);
        mAvatarImageView = findViewById(R.id.tag_user_avatar);
        mAvatarnormal = findViewById(R.id.tag_user_normal);
        ivTag = findViewById(R.id.tag_user_icon);
        mAvatarImageView.setBorderColor(mBorderColor);
        mAvatarImageView.setBorderWidth(mBorderWidth);
        setImageResource(mDefaultAvatar);
        setCurrTag(mCurrTag);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //重新定义认证标签 tag
        int width = getMeasuredWidth();//64 -> 20 || x > y
        int tagWidth = (int) (0.3125f * width);//64 -> 20 约等于 0.3125
        ViewGroup.LayoutParams layoutParams = ivTag.getLayoutParams();
        layoutParams.width = tagWidth;
        layoutParams.height = tagWidth;
        ivTag.setLayoutParams(layoutParams);
    }

    //更新当前的身份认证标识
    private void upStatus() {
        if (mCurrTag == TAG_ENTERPRISE) {
            ivTag.setImageResource(R.drawable.user_center_enterprise_certification);
            ivTag.setVisibility(VISIBLE);
        } else if (mCurrTag == TAG_PERSONAL) {
            ivTag.setImageResource(R.drawable.user_center_personal_certification);
            ivTag.setVisibility(VISIBLE);
        } else {
            ivTag.setVisibility(INVISIBLE);
        }
    }

    //设置当前的身份认证标识
    public void setCurrTag(int mCurrTag) {
        this.mCurrTag = mCurrTag;
        upStatus();
    }

//    public void setCurrTag(UserInfoDetail info) {
//        if (info != null && info.getIdentityVerifyStatus() == 1) {
//            if (info.getIdentityVerifyType() == 2) {
//                setCurrTagEnterprise();
//            } else {
//                setCurrTagPersonal();
//            }
//        } else {
//            setCurrTag(TAG_DEFAULT);
//        }
//    }

    //设置当前默认背景图
    public void setImageResource(int resId) {
        this.mDefaultAvatar = resId;
        mAvatarImageView.setImageResource(mDefaultAvatar);
        mAvatarnormal.setImageResource(mDefaultAvatar);
    }

    //是否显示圆形图片
    public void setTurnOn(boolean turnOn) {
        if (turnOn) {
            mAvatarImageView.setVisibility(VISIBLE);
            mAvatarnormal.setVisibility(GONE);
        } else {
            mAvatarImageView.setVisibility(GONE);
            mAvatarnormal.setVisibility(VISIBLE);
        }
    }

    //设置当前身份为个人认证
    public void setCurrTagPersonal() {
        setCurrTag(TAG_PERSONAL);
    }

    //设置当前身份为企业认证
    public void setCurrTagEnterprise() {
        setCurrTag(TAG_ENTERPRISE);
    }

    //设置当前身份为企业认证
    public void setCurrTagDefaullt() {
        setCurrTag(TAG_DEFAULT);
    }

    //从网络加载图片到当前view
    public void loadPic(Object object) {
        Glide.with(getContext()).load(object).apply(mOptions).into(mAvatarImageView);
        Glide.with(getContext()).load(object).apply(mOptions).into(mAvatarnormal);
    }
}

