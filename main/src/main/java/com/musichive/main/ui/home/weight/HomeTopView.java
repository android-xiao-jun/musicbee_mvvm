package com.musichive.main.ui.home.weight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.FloatRange;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.musichive.main.R;
import com.musichive.aop.SingleClick;


/**
 * @Author Jun
 * Date 2021 年 03月 29 日 16:07
 * Description android_client
 */
public class HomeTopView extends LinearLayout {

    private ImageView ivHomeTopLogo;
    private ImageView ivSearchHome;
    private View view_bg;
    private View cl_bg;

    private boolean isWhite = false;
    private int dp_150;
    private Drawable home_top_bg;

    public HomeTopView(Context context) {
        this(context, null);
    }

    public HomeTopView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeTopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.common_home_layout_header_top, this);
        initView();
        dp_150 = getResources().getDimensionPixelSize(R.dimen.dp_150);
        home_top_bg = ResourcesCompat.getDrawable(getResources(), R.drawable.home_top_bg, null);
    }

    private void initView() {
        ivHomeTopLogo = (ImageView) findViewById(R.id.iv_home_top_logo);
        ivSearchHome = findViewById(R.id.iv_search_home);
        view_bg = findViewById(R.id.view_bg);
        cl_bg = findViewById(R.id.cl_bg);
        ivSearchHome.setOnClickListener(new OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View view) {

            }
        });
    }

    private int scrollRecyclerY = 0;

    public void setRecyclerViewScrollY(int scrollY) {
        this.scrollRecyclerY = scrollY;
        float persent = scrollRecyclerY * 1f / (dp_150);//最大滚动量
        if (persent > 1f) {
            persent = 1f;
        }
        if (persent < 0f) {
            persent = 0f;
        }
        setStatus(persent);
    }

    public void initTop() {
        RecyclerView homeRecyclerView = (getRootView()).findViewById(R.id.homeRecyclerView);
        if (homeRecyclerView != null) {
            bindRecyclerView(homeRecyclerView);
        }
    }

    public void resetY(){
        scrollRecyclerY = 0;
    }

    public void bindRecyclerView(RecyclerView recyclerView) {
        resetY();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                scrollRecyclerY += dy;
                setRecyclerViewScrollY(scrollRecyclerY);
            }
        });

    }

    //设置当前背景显示的透明度
    public void setStatus(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        isWhite = alpha >= 0.5f;
//        setAlpha(alpha);
        //根据透明度修改背景颜色
        int a = (int) (255 * alpha);
        if (home_top_bg != null) {
            home_top_bg.setAlpha(a);
            cl_bg.setBackground(home_top_bg);
        } else {
            int color = Color.argb(a, 255, 255, 255);
            cl_bg.setBackgroundColor(color);
        }
        upStatus();
    }

    public void upStatus() {
        if (isWhite) {//白色背景下样式
            ivHomeTopLogo.setImageResource(R.drawable.home_logo_icon_b);
            view_bg.setVisibility(GONE);
            ivSearchHome.setImageResource(R.drawable.icon_search_home_top_b);
        } else {
            ivHomeTopLogo.setImageResource(R.drawable.home_logo_icon_w);
            view_bg.setVisibility(VISIBLE);
            ivSearchHome.setImageResource(R.drawable.icon_search_home_top_w);
        }
        if (statusBgListener != null) {
            statusBgListener.onStatus(isWhite);
        }
    }

    private HomeBannerTopStatusBgListener statusBgListener;

    public void setStatusBgListener(HomeBannerTopStatusBgListener statusBgListener) {
        this.statusBgListener = statusBgListener;
    }

    public interface HomeBannerTopStatusBgListener {
        void onStatus(boolean b);
    }
}
