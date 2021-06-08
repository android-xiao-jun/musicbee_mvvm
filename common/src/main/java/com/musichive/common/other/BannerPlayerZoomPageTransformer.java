package com.musichive.common.other;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.SizeUtils;
import com.musichive.common.R;

import org.jetbrains.annotations.NotNull;


/**
 * @Author Jun
 * Date 2021 年 06月 03 日 09:11
 * Description 音乐蜜蜂-mvvm版本
 */
public class BannerPlayerZoomPageTransformer implements ViewPager2.PageTransformer {
    private static final float DEFAULT_MIN_SCALE = 0.75f;

    @Override
    public void transformPage(@NonNull @NotNull View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

         if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            float scaleFactor = Math.max(DEFAULT_MIN_SCALE, 1 - Math.abs(position));
            float vertMargin = pageHeight * (1 - scaleFactor) / 2;
            float horzMargin = pageWidth * (1 - scaleFactor) / 2;
            if (position < 0) {
                view.setTranslationX(horzMargin - vertMargin / 2);
            } else {
                view.setTranslationX(-horzMargin + vertMargin / 2);
            }
            if (scaleFactor>1f){
                scaleFactor=1f;
            }
            //设置动画原点
            view.setPivotX(0);
            view.setPivotY(0);
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }

        View bgCd = view.findViewById(R.id.rl_bg);
        if (bgCd != null) {
            int default_width = SizeUtils.dp2px(188);
            float outWidth = position * default_width / 2;
            if (outWidth<0){
                bgCd.setTranslationX(outWidth);
            }else {
                bgCd.setTranslationX(0);
            }
        }
    }


}
