package com.musichive.common.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.request.RequestOptions;
import com.musichive.base.glide.GlideUtils;
import com.musichive.common.BuildConfig;
import com.musichive.common.R;
import com.musichive.common.api.RetrofitApi;
import com.musichive.common.api.interceptor.HeaderInterceptor;
import com.musichive.common.api.interceptor.LogInterceptor;
import com.musichive.common.api.interceptor.SignatureInterceptor;
import com.musichive.common.config.AppConfig;
import com.musichive.common.other.float_player.PlayerTool;
import com.musichive.common.other.fps.FPSTool;
import com.musichive.common.player.PlayerManager;
import com.musichive.common.ui.home.repository.HomeDataRepository;
import com.musichive.common.ui.player.activity.PlayerActivity;
import com.musichive.common.utils.LogUtils;
import com.musichive.common.utils.ToastUtils;
import com.musichive.common.weight.RefreshLoadHeader;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * @Author Jun
 * Date 2021 年 05月 27 日 15:35
 * Description 音乐蜜蜂-mvvm版本
 */
public class BaseApplication extends com.kunminx.architecture.BaseApplication {

    public static BaseApplication mInstance;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PlayerManager.getInstance().init(this);
        mInstance = this;
        //======================== 可以延迟加载(下面) 之后优化加载 主线程 ========================
        //常用工具类(第一个初始化)
        Utils.init(this);
        //日志(必须Utils.init之后)
        LogUtils.init(BuildConfig.DEBUG, "音乐蜜蜂-mvvm版本");
        //吐司提示(必须Utils.init之后)
        ToastUtils.init(this);
        //预加载网络请求
        RetrofitApi.getInstance().addClient(AppConfig.NetWork.BASE_URL, getOkHttpClient());
        RetrofitApi.getInstance().addClient(AppConfig.NetWork.BASE_URL2, getOkHttpClient());
        RetrofitApi.getInstance().getRetrofit(AppConfig.NetWork.BASE_URL);
        RetrofitApi.getInstance().getRetrofit(AppConfig.NetWork.BASE_URL2);
        HomeDataRepository.getInstance().obtainImageUrlPrefix();
        //图片加载初始化
        GlideUtils.init(new RequestOptions()
                .placeholder(R.drawable.default_pic)
                .error(R.drawable.default_pic)
        );
        //下拉上拉全局样式
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> new RefreshLoadHeader(context));
        SmartRefreshLayout.setDefaultRefreshFooterCreator((c, l) -> new ClassicsFooter(c));
        initFPS(this);
        //(必须Utils.init之后)
        initPlayerFloat(this);
        //======================== 可以延迟加载(上面) ========================
    }

    private OkHttpClient okHttpClient = null;

    private OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(AppConfig.NetWork.connectTimeoutMills, TimeUnit.MILLISECONDS);
            builder.readTimeout(AppConfig.NetWork.readTimeoutMills, TimeUnit.MILLISECONDS);

            builder.addInterceptor(new HeaderInterceptor());
            builder.addInterceptor(new SignatureInterceptor());
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(new LogInterceptor());
            }
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

    private void initFPS(Application application) {
        if (BuildConfig.DEBUG) {
            FPSTool.getInstance().start(application);
        }
    }

    private void initPlayerFloat(Application application) {
        PlayerTool.getInstance().start(application);
        PlayerTool.getInstance().setOnClickListener(v -> {
            ActivityUtils.getTopActivity().startActivity(new Intent(ActivityUtils.getTopActivity(), PlayerActivity.class));
        });
    }
}
