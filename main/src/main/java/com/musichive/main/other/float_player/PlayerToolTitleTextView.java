package com.musichive.main.other.float_player;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.musichive.main.R;


/**
 * @Author Jun
 * Date 2021 年 06月 22 日 13:37
 * Description 标题和作者view（方便替换）
 */
public class PlayerToolTitleTextView extends LinearLayout {

    public String TAG = "PlayerToolTitleTextView";


    public PlayerToolTitleTextView(Context context) {
        this(context, null);
    }

    public PlayerToolTitleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        TextView textViewTitle = initSongName(context, null);
        addView(textViewTitle);

        TextView textViewAuthor = initAuthorName(context, null);
        LayoutParams layoutParams = new LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layoutParams.weight = 1;
        addView(textViewAuthor, layoutParams);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
    }


    private static TextView initSongName(Context context, @Nullable AttributeSet attrs) {
        TextView textView = new TextView(context, attrs);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//        textView.setMaxWidth(context.getResources().getDimensionPixelOffset(R.dimen.design_160dp));
        textView.setMaxLines(1);
        textView.setTextColor(Color.parseColor("#1e1e1e"));
        textView.setTextSize(12);
        textView.setText("暂无歌曲");
        textView.setEllipsize(TextUtils.TruncateAt.END);
        return textView;
    }

    private static TextView initAuthorName(Context context, @Nullable AttributeSet attrs) {
        TextView textView = new TextView(context, attrs);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        textView.setMinWidth(context.getResources().getDimensionPixelOffset(R.dimen.design_80dp));
        textView.setMaxLines(1);
        textView.setTextColor(Color.parseColor("#999999"));
        textView.setTextSize(12);
        textView.setText(" ");
        textView.setEllipsize(TextUtils.TruncateAt.END);
        return textView;
    }

    public TextView getSongNameView() {
       return (TextView) getChildAt(0);
    }


    public TextView getAuthorNameView() {
        return (TextView) getChildAt(1);
    }

    public boolean initLyricString(String mPlayLyricPath) {
//        try {
//            LrcSingleLineView songNameView = (LrcSingleLineView) getSongNameView();
//            if (songNameView == null) {
//                return false;
//            }
//            return songNameView.initLyricString(mPlayLyricPath);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return false;
    }

    public void updateLyrics(int currentPosition) {
//        try {
//            LrcSingleLineView songNameView = (LrcSingleLineView) getSongNameView();
//            if (songNameView == null) {
//                return;
//            }
//            boolean b = songNameView.updateLyrics(currentPosition);
//            songNameView.setEllipsize(b ? TextUtils.TruncateAt.MARQUEE : TextUtils.TruncateAt.END);
//            //滚动歌词一行的话，就不显示作者，显示歌词
//            getAuthorNameView().setVisibility(b ? GONE : VISIBLE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
