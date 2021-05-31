package com.musichive.common.ui.home.viewmodel;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.ViewModel;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 16:03
 * Description 音乐蜜蜂-mvvm版本
 */
public class HomeViewModel extends ViewModel {
    public final ObservableBoolean initTabAndPage = new ObservableBoolean();

    {
        initTabAndPage.set(true);
    }
}
