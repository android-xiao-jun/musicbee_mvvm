package com.musichive.common.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 18:50
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeFragmentViewModel extends ViewModel {
    public final ObservableField<String> indexStr = new ObservableField<String>();
}
