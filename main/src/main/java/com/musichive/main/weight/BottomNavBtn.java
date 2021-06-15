package com.musichive.main.weight;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.musichive.main.R;


/**
 * @author <a href="mailto:hejunqiu@everimaging.com">hejunqiu</a>
 * @version 1.0
 * @description
 * @create 18-12-25 : 上午10:53
 */
public class BottomNavBtn extends RelativeLayout {

    private ImageView mIconView;
    private ImageView mIconView2;

    private TextView mTextView;


    public BottomNavBtn(Context context) {
        this(context, null);
    }

    public BottomNavBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomNavBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponents(context);
    }

    public ImageView getIconView() {
        return mIconView;
    }

    public ImageView getIconView2() {
        return mIconView2;
    }

    public TextView getmTextView() {
        return mTextView;
    }

    private void initComponents(Context context) {
        inflate(context, R.layout.bottom_nav_btn_layout, this);

        mIconView = findViewById(R.id.bottom_nav_btn_icon);
        mIconView2 = findViewById(R.id.bottom_nav_btn_icon_2);

        mTextView = findViewById(R.id.bottom_nav_btn_text);
        mTextView.setVisibility(GONE);

    }

    public void setImageResource(int resId){
        if(mTextView.getVisibility()==GONE){
            mIconView.setVisibility(GONE);
            mIconView2.setVisibility(VISIBLE);
            mIconView2.setImageResource(resId);
        }else{
            mIconView.setVisibility(VISIBLE);
            mIconView2.setVisibility(GONE);
            mIconView.setImageResource(resId);
        }
    }

    public void setTextResId(int resId){
        if(resId != -1){
            mTextView.setText(resId);
            mTextView.setVisibility(VISIBLE);
        }else{
            mTextView.setVisibility(GONE);
        }
    }
    public void setTextResId(String str){
        if(!TextUtils.isEmpty(str)){
            mTextView.setText(str);
            mTextView.setVisibility(VISIBLE);
        }else{
            mTextView.setVisibility(GONE);
        }
    }

}