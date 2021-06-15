package com.musichive.main.utils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @Author Jun
 * Date 2021 年 06月 03 日 09:54
 * Description 音乐蜜蜂-mvvm版本
 */
public class ProgressTimeUtils {
    public static String formatTimeIntervalMinSec(long length) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long numMinutes = timeUnit.toMinutes(length);
        long numSeconds = timeUnit.toSeconds(length);
        return String.format(Locale.getDefault(), "%02d:%02d", numMinutes, numSeconds % 60);
    }
}
