package com.musichive.musicbee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.kunminx.architecture.ui.page.BaseActivity;
import com.kunminx.architecture.ui.page.DataBindingConfig;
import com.musichive.common.ui.home.activity.HomeActivity;
import com.musichive.common.utils.HandlerUtils;
import com.musichive.common.utils.LogUtils;
import com.musichive.musicbee.BR;
import com.musichive.musicbee.bean.Advertisement;
import com.musichive.musicbee.viewmodel.SplashViewModel;

import java.util.List;

import me.jessyan.autosize.internal.CancelAdapt;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 15:42
 * Description 音乐蜜蜂-mvvm版本
 */
public class SplashActivity extends BaseActivity implements CancelAdapt {

    private SplashViewModel splashViewModel;
    private long skipCurrentTime = 5L;

    @Override
    protected void initViewModel() {
        splashViewModel = getActivityScopeViewModel(SplashViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.app_activity_splash, BR.viewModel, splashViewModel)
                .addBindingParam(BR.skipClick, skipClick);
    }

    private View.OnClickListener skipClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HandlerUtils.getInstance().getMainHander().removeCallbacks(skipSecond);
            HandlerUtils.getInstance().getMainHander().removeCallbacks(skipHomeRunnable);
            HandlerUtils.getInstance().getMainHander().post(skipHomeRunnable);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (getIntent() != null && (getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            LogUtils.w("FLAG_ACTIVITY_BROUGHT_TO_FRONT");
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        splashViewModel.adList.observe(this, advertisements -> {
            showAd(advertisements);
        });
        splashViewModel.requestSplashAd();
        HandlerUtils.getInstance().getMainHander().postDelayed(skipHomeRunnable, 200);
    }

    private void showAd(List<Advertisement> advertisements) {
        HandlerUtils.getInstance().getMainHander().removeCallbacks(skipHomeRunnable);
        Advertisement advertisementData = splashViewModel.getAdvertisementData(advertisements);

        if (advertisements == null || advertisements.isEmpty() || advertisementData == null) {
            HandlerUtils.getInstance().getMainHander().post(skipHomeRunnable);
        } else {
            skipCurrentTime = advertisementData.getDisplayTime() / 1000;
            splashViewModel.picUrl.set(advertisementData.getCover());
            upStatusTextValue();
            HandlerUtils.getInstance().getMainHander().postDelayed(skipSecond, 1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HandlerUtils.getInstance().getMainHander().removeCallbacks(skipHomeRunnable);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void upStatusTextValue() {
        splashViewModel.skipSecond.set("跳过" + skipCurrentTime + "s");
        splashViewModel.skipShow.set(true);
    }

    private Runnable skipSecond = new Runnable() {
        @Override
        public void run() {
            skipCurrentTime = skipCurrentTime - 1;
            upStatusTextValue();
            if (skipCurrentTime > 0) {
                HandlerUtils.getInstance().getMainHander().post(skipHomeRunnable);
            } else {
                HandlerUtils.getInstance().getMainHander().postDelayed(skipSecond, 1000);
            }
        }
    };

    private Runnable skipHomeRunnable = () -> {
        startActivity(new Intent(this, HomeActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    };
}
