package com.musichive.musicbee.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.kunminx.architecture.data.response.DataResult;
import com.kunminx.architecture.data.response.ResponseStatus;
import com.musichive.base.glide.GlideUtils;
import com.musichive.common.api.RetrofitApi;
import com.musichive.common.app.AppSaveUtils;
import com.musichive.common.bean.ModelSubscriber;
import com.musichive.common.config.AppConfig;
import com.musichive.common.utils.LogUtils;
import com.musichive.musicbee.api.AppService;
import com.musichive.musicbee.bean.Advertisement;
import com.musichive.musicbee.bean.AdvertisementBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 15:47
 * Description 音乐蜜蜂-mvvm版本
 */
public class SplashViewModel extends AndroidViewModel {

    public MutableLiveData<List<Advertisement>> adList = new MutableLiveData<>();

    public ObservableField<String> picUrl = new ObservableField<>();
    public ObservableField<String> skipSecond = new ObservableField<>();
    public ObservableBoolean skipShow = new ObservableBoolean();


    public SplashViewModel(Application application) {
        super(application);
        String oldAd = AppSaveUtils.getInstance().getString(AppSaveUtils.KYE_SPLASH_AD);
        if (!TextUtils.isEmpty(oldAd)) {
            List<Advertisement> advertisements = JSON.parseArray(oldAd, Advertisement.class);
            adList.postValue(advertisements);
        } else {
            adList.postValue(new ArrayList<>());
        }
        skipShow.set(false);
        skipSecond.set("跳过5s");
    }

    public void requestSplashAd() {
        getListByAdType(dataResult -> {
            if (dataResult.getResponseStatus().isSuccess() && dataResult.getResult() != null && dataResult.getResult().getAdvertisements() != null) {
                List<Advertisement> advertisements = dataResult.getResult().getAdvertisements();
                AppSaveUtils.getInstance().saveString(AppSaveUtils.KYE_SPLASH_AD, JSON.toJSONString(advertisements));
                Advertisement advertisementData = getAdvertisementData(advertisements);
                if (advertisementData != null) {
                    GlideUtils.preload(getApplication(), advertisementData.getCover());
                }
                adList.postValue(advertisements);
                LogUtils.w("闪屏页广告 获取成功");
            } else {
                AppSaveUtils.getInstance().saveString(AppSaveUtils.KYE_SPLASH_AD, "");
                LogUtils.w("闪屏页广告 获取失败");
                adList.postValue(null);
            }
        });
    }

    public Advertisement getAdvertisementData(List<Advertisement> advertisements) {
        if (advertisements == null || advertisements.isEmpty()) {
            return null;
        }
        if (advertisements.size() == 1) {
            Advertisement targetAd = advertisements.get(0);
            if (!targetAd.isExpire()) {
                return targetAd;
            }
            return null;
        } else {
            int index = (int) (Math.random() * (advertisements.size()));
            Advertisement targetAd = advertisements.remove(index);
            if (targetAd.isExpire()) {
                return getAdvertisementData(advertisements);
            } else {
                return targetAd;
            }
        }
    }

    public static void getListByAdType(DataResult.Result<AdvertisementBean> result) {
        AppService service = RetrofitApi.getInstance().getService(AppConfig.NetWork.BASE_URL, AppService.class);
        RetrofitApi.addSubscribe(service.getListByAdType("flash-screen", "flash-screen"))
                .subscribe(new ModelSubscriber<AdvertisementBean>() {
                    @Override
                    public void onSuccess(AdvertisementBean data) {
                        result.onResult(new DataResult<>(data, new ResponseStatus()));
                    }

                    @Override
                    public void onFailure(String errorCode) {
                        result.onResult(new DataResult<>(null, new ResponseStatus(errorCode, false)));
                    }
                });

    }
}
