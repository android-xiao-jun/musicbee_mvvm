package com.musichive.common.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kennyc.view.MultiStateView;
import com.musichive.common.R;
import com.musichive.common.aop.SingleClick;

import static com.musichive.common.ui.home.viewmodel.MusicLibFragmentChildViewModel.STATE_CONTENT_EMPTY;
import static com.musichive.common.ui.home.viewmodel.MusicLibFragmentChildViewModel.STATE_CONTENT_END;
import static com.musichive.common.ui.home.viewmodel.MusicLibFragmentChildViewModel.STATE_CONTENT_ERROR;

/**
 * @Author Jun
 * Date 2021 年 06月 10 日 11:29
 * Description 音乐蜜蜂-mvvm版本
 */
public class AppMultiStateView extends MultiStateView {
    public AppMultiStateView(Context context) {
        super(context);
        init();
    }

    public AppMultiStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AppMultiStateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.AppMultiStateView);
        if (a.hasValue(R.styleable.AppMultiStateView_emptySrc)) {
            setEmptySrc(a.getInt(R.styleable.AppMultiStateView_emptySrc, R.drawable.no_market));
        }
        if (a.hasValue(R.styleable.AppMultiStateView_emptyStr)) {
            setEmptyStr(a.getString(R.styleable.AppMultiStateView_emptySrc));
        }

        a.recycle();
        init();
    }

    public void init() {
        setViewForState(R.layout.common_loading_view, VIEW_STATE_LOADING);
        setViewForState(R.layout.error_net_work_common, VIEW_STATE_ERROR);
        setViewForState(R.layout.common_no_data_multi, VIEW_STATE_EMPTY);
        OnClickListener onClickListener = new OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                if (statusListener==null){
                    return;
                }
                statusListener.onRefresh();
            }
        };
        findViewById(R.id.no_follow_discover).setOnClickListener(onClickListener);
        findViewById(R.id.empty_btn_view).setOnClickListener(onClickListener);
    }

    public void setEmptyStr(String str) {
        TextView textView = findViewById(R.id.empty_text_view);
        textView.setText(str);
    }

    public void setEmptySrc(int src) {
        ImageView imageView = findViewById(R.id.empty_image_view);
        imageView.setImageResource(src);
    }

    public void showContent() {
        setViewState(VIEW_STATE_CONTENT);
    }

    public void showEmpty() {
        setViewState(VIEW_STATE_EMPTY);
    }

    public void showError() {
        setViewState(VIEW_STATE_ERROR);
    }

    public void showLoading() {
        setViewState(VIEW_STATE_LOADING);
    }

    public void bindState(int state) {
        if (state == STATE_CONTENT_ERROR) {
            showError();
        } else if (state == STATE_CONTENT_END) {
            showContent();
        } else if (state == STATE_CONTENT_EMPTY) {
            showEmpty();
        } else {
            showLoading();
        }
    }

    private StatusListener statusListener;

    public void setStatusListener(StatusListener statusListener) {
        this.statusListener = statusListener;
    }

    public interface StatusListener {
        void onRefresh();
    }

}
