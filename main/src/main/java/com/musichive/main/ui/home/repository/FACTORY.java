package com.musichive.main.ui.home.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.musichive.main.ui.home.viewmodel.HomeFragmentViewModel;
import com.musichive.main.ui.home.viewmodel.NFTFragmentViewModel;

/**
 * @Author Jun
 * Date 2021 年 06月 02 日 10:23
 * Description 音乐蜜蜂-mvvm版本
 */
public class FACTORY implements ViewModelProvider.Factory {

    public FACTORY(HomeDataRepository homeDataRepository) {
        this.homeDataRepository = homeDataRepository;
    }

    private HomeDataRepository homeDataRepository;

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (HomeFragmentViewModel.class.equals(modelClass)) {
            HomeFragmentViewModel homeFragmentViewModel = new HomeFragmentViewModel(homeDataRepository);
            return (T) homeFragmentViewModel;
        } else if (NFTFragmentViewModel.class.equals(modelClass)) {
            NFTFragmentViewModel nftFragmentViewModel = new NFTFragmentViewModel(homeDataRepository);
            return (T) nftFragmentViewModel;
        } else {
            try {
                return modelClass.newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
