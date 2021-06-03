package com.musichive.common.ui.player.weight;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.musichive.common.R;

/**
 * @Author Jun
 * Date 2021 年 04月 20 日 09:31
 * Description 市集分类可见类型
 */
public class MarketTypeInfoView extends LinearLayout {

    //转让形式
    private TextView tv1;
    private View line1;
    //歌曲 或者 demo 或者其他
    private TextView tv2;

    //蓝v黄v
    private ImageView iv_vv;
    private TextView tv_time;
    private View line_time;

    public MarketTypeInfoView(Context context) {
        this(context, null);
    }

    public MarketTypeInfoView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarketTypeInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_market_type_info_view, this);
        setGravity(Gravity.CENTER_VERTICAL);
        initView();
    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv_1);
        iv_vv = (ImageView) findViewById(R.id.iv_v);
        tv_time = (TextView) findViewById(R.id.tv_time);
        line_time = (View) findViewById(R.id.line_time);
        line1 = (View) findViewById(R.id.line_1);
        tv2 = (TextView) findViewById(R.id.tv_2);
    }

    public void setMarketInfo(String certificationType,String time,String year){
        tv1.setVisibility(VISIBLE);
        iv_vv.setVisibility(VISIBLE);
        tv_time.setVisibility(VISIBLE);
        line_time.setVisibility(VISIBLE);
        iv_vv.setImageResource(R.drawable.new_yv);
        tv1.setText(year);
        tv_time.setText(time);
        tv_time.setText(time);
        tv2.setVisibility(GONE);
        line1.setVisibility(GONE);

    }

    /**
     * @param year 授权年限 三年还是五年还是终生
     * @param type 歌曲还是demo
     */
    public void setStatusText(String year, String type) {
        tv1.setText(year);
        tv2.setText(type);
        tv1.setVisibility(VISIBLE);
        tv2.setVisibility(VISIBLE);
    }


    /**
     * 仅仅显示文本（市集列表）
     *
     * @param year
     * @param type
     */
    public void onlyShowText(String year, String type) {
        setStatusText(year, type);
    }

    /**
     * 仅仅显示标签（市集列表）
     *
     * @param author
     */
    public void onlyShowTag(String author) {
        line1.setVisibility(GONE);
        tv1.setVisibility(GONE);
        tv2.setVisibility(GONE);
    }
}
