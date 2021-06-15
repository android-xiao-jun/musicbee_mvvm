package com.musichive.main.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import androidx.multidex.MultiDex;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.request.RequestOptions;
import com.musichive.base.glide.GlideUtils;
import com.musichive.main.BuildConfig;
import com.musichive.main.R;
import com.musichive.main.api.RetrofitApi;
import com.musichive.main.api.interceptor.HeaderInterceptor;
import com.musichive.main.api.interceptor.LogInterceptor;
import com.musichive.main.api.interceptor.SignatureInterceptor;
import com.musichive.main.config.AppConfig;
import com.musichive.main.other.float_player.PlayerTool;
import com.musichive.main.other.fps.FPSTool;
import com.musichive.main.player.PlayerManager;
import com.musichive.main.player.helper.PlayerDataTransformUtils;
import com.musichive.main.room.MusicDao;
import com.musichive.main.room.MusicDatabase;
import com.musichive.main.room.MusicEntity;
import com.musichive.main.ui.home.repository.HomeDataRepository;
import com.musichive.main.ui.player.activity.PlayerActivity;
import com.musichive.main.utils.HandlerUtils;
import com.musichive.main.utils.LogUtils;
import com.musichive.main.utils.ToastUtils;
import com.musichive.main.weight.RefreshLoadHeader;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.List;
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
        mInstance = this;
        if (shouldInit()) {
            PlayerManager.getInstance().init(this);
            //常用工具类(第一个初始化)
            Utils.init(this);
            //日志(必须Utils.init之后)
            LogUtils.init(BuildConfig.DEBUG, "音乐蜜蜂-mvvm版本");
            //保存数据 闪屏页有使用到
            AppSaveUtils.getInstance().init(this);
            //吐司提示(必须Utils.init之后)-网络变化有使用到
            ToastUtils.init(this);
            //======================== 可以延迟加载(下面) 之后优化加载 主线程 ========================
            HandlerUtils.getInstance().postWork(()->{
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

                initPlayerData(this);
            });
            HandlerUtils.getInstance().getMainHander().postDelayed(()->{
                //(必须Utils.init之后)
                initPlayerFloat(this);
            },100);
            //======================== 可以延迟加载(上面) ========================
        }
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

    private boolean shouldInit() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = Process.myPid();
        if (processInfos == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    private void initFPS(Application application) {
        if (BuildConfig.DEBUG) {
            FPSTool.getInstance().start(application);
        }
    }

    private void initPlayerFloat(Application application) {
        PlayerTool.getInstance().start(application);
        PlayerTool.getInstance().setOnClickListener(v -> {
            Intent intent = new Intent(ActivityUtils.getTopActivity(), PlayerActivity.class);
            ActivityUtils.getTopActivity().startActivity(intent);
        });
    }

    private void initPlayerData(Application application) {
        //从数据库中恢复数据
        HandlerUtils.getInstance().postWork(() -> {
            MusicDao musicDao = MusicDatabase.getInstance(application).musicDao();
            List<MusicEntity> studentList = musicDao.getMusicList();
            if (studentList != null && !studentList.isEmpty()) {
                PlayerDataTransformUtils.transformHomeMusic(studentList);
            }
        });
    }
}
