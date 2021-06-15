package com.musichive.main.api.interceptor;

import com.musichive.main.config.AppConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 16:32
 * Description 音乐蜜蜂-mvvm版本
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", AppConfig.NetWork.CONTENT_TYPE);
        builder.addHeader("Accept", AppConfig.NetWork.ACCEPT);
        builder.addHeader("Accept-Encoding", AppConfig.NetWork.ACCEPT_ENCODING);
        builder.addHeader("PBClientID", AppConfig.Device.Android_ID);
        Request request = builder.build();
        Response response = chain.proceed(request);
        return response;
    }
}
