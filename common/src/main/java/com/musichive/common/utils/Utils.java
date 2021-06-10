package com.musichive.common.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.musichive.common.bean.home.Sign;

import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 06月 10 日 14:58
 * Description 音乐蜜蜂-mvvm版本
 */
public class Utils {
    public static String getPerformername(String signs) {
        if (signs == null) {
            return "";
        }
        List<Sign> signList = JSON.parseArray(signs, Sign.class);
        String result = null;
        if (signList != null && signList.size() > 0) {
            for (int i = 0; i < signList.size(); i++) {
                if (TextUtils.equals(signList.get(i).getType(), "singer")) {
                    result = signList.get(i).getName();//表演者
                    if (!"".equals(result) && !TextUtils.isEmpty(result)) {
                        break;
                    }
                } else if (TextUtils.equals(signList.get(i).getType(), "write_music")) {
                    result = signList.get(i).getName();//曲作者
                    if (!"".equals(result) && !TextUtils.isEmpty(result)) {
                        break;
                    }
                } else if (TextUtils.equals(signList.get(i).getType(), "write_lyric")) {
                    result = signList.get(i).getName();//词作者
                    if (!"".equals(result) && !TextUtils.isEmpty(result)) {
                        break;
                    }
                } else if (TextUtils.equals(signList.get(i).getType(), "transcribe")) {
                    result = signList.get(i).getName();//录音制作者
                    if (!"".equals(result) && !TextUtils.isEmpty(result)) {
                        break;
                    }
                }
            }
        }
        return result == null ? "" : result;
    }
}
