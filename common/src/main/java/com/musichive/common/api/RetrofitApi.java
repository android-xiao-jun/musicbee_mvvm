package com.musichive.common.api;

import android.text.TextUtils;

import com.musichive.common.api.converter.FastJsonConverterFactory;
import com.musichive.common.config.AppConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 16:06
 * Description 音乐蜜蜂-mvvm版本
 */
public class RetrofitApi {
    private volatile static RetrofitApi mInstance;
    public static final long connectTimeoutMills = AppConfig.NetWork.connectTimeoutMills;
    public static final long readTimeoutMills = AppConfig.NetWork.readTimeoutMills;
    private Map<String, Retrofit> retrofitMap = new HashMap<>();
    private Map<String, OkHttpClient> clientMap = new HashMap<>();

    private RetrofitApi() {

    }

    public static RetrofitApi getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitApi.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitApi();
                }
            }
        }
        return mInstance;
    }

    public Retrofit getRetrofit(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (retrofitMap.get(baseUrl) != null) {
            return retrofitMap.get(baseUrl);
        }
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getClient(baseUrl))
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        retrofitMap.put(baseUrl, retrofit);
        return retrofit;
    }

    public <Service> Service getService(String baseUrl, Class<Service> service) {
        return getInstance().getRetrofit(baseUrl).create(service);
    }

    public void addClient(String baseUrl, OkHttpClient okHttpClient) {
        clientMap.put(baseUrl, okHttpClient);
    }

    private OkHttpClient getClient(String baseUrl) {
        if (TextUtils.isEmpty(baseUrl)) {
            throw new IllegalStateException("baseUrl can not be null");
        }
        if (clientMap.get(baseUrl) != null) {
            return clientMap.get(baseUrl);
        }

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(connectTimeoutMills, TimeUnit.MILLISECONDS);
        builder.readTimeout(readTimeoutMills, TimeUnit.MILLISECONDS);

        OkHttpClient client = builder.build();
        clientMap.put(baseUrl, client);
        return client;
    }

    public static <T> Observable<T> addSubscribe(Observable<T> observable) {
        return addSubscribe(observable, false);
    }

    public static <T> Observable<T> addSubscribe(Observable<T> observable, boolean main) {
        if (!main) {
            return observable.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io());
        }
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void clearCache() {
        getInstance().retrofitMap.clear();
        getInstance().clientMap.clear();
    }

}
