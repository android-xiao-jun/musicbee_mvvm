package com.musichive.common.api.interceptor;

import com.musichive.common.utils.LogUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 16:50
 * Description 音乐蜜蜂-mvvm版本
 */
public class LogInterceptor implements Interceptor {
    public static final String TAG = "LogInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        Request request = builder.build();
        logRequest(request);
        Response response = getCookie(chain, request);//TODO 添加--获取cookie
        return logResponse(response);
    }

    private void logRequest(Request request) {
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            LogUtils.d(TAG, "url : " + url);
            LogUtils.d(TAG, "method : " + request.method());
            if (headers != null && headers.size() > 0) {
                LogUtils.d(TAG, "headers : " + headers.toString());
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        LogUtils.d(TAG, "params : " + bodyToString(request));
                    } else {
                        LogUtils.d(TAG, "params : " + " maybe [file part] , too large too print , ignored!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Response logResponse(Response response) {
        try {
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            ResponseBody body = clone.body();
            if (body != null) {
                MediaType mediaType = body.contentType();
                if (mediaType != null) {
                    if (isText(mediaType)) {
                        String resp = body.string();
                        LogUtils.json(TAG, resp);

                        body = ResponseBody.create(mediaType, resp);
                        return response.newBuilder().body(body).build();
                    } else {
                        LogUtils.d(TAG, "data : " + " maybe [file part] , too large too print , ignored!");
                    }
                } else {
                    //打印返回数据
                    String resp = body.string();
                    LogUtils.d(TAG, "ResponseBody : " + resp);
                    body = ResponseBody.create(mediaType, resp);
                    return response.newBuilder().body(body).build();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType == null) return false;

        return ("text".equals(mediaType.subtype())
                || "json".equals(mediaType.subtype())
                || "xml".equals(mediaType.subtype())
                || "html".equals(mediaType.subtype())
                || "webviewhtml".equals(mediaType.subtype())
                || "x-www-form-urlencoded".equals(mediaType.subtype()));
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final Exception e) {
            return "something error when show requestBody.";
        }
    }

    private Response getCookie(Chain chain, Request request) throws IOException {
        // 获取 Cookie
        Response resp = chain.proceed(request);
        List<String> setCookies = resp.headers("Set-Cookie");
        String cookieStr = "";
        if (setCookies != null && setCookies.size() > 0) {
            String s = setCookies.get(0);
            //sessionid值格式：JSESSIONID=AD5F5C9EEB16C71EC3725DBF209F6178，是键值对，不是单指值
            cookieStr = s.substring(0, s.indexOf(";"));
            //持久化到本地
//            SystemParams.getInstance().setString("COOKIES", cookieStr);
            LogUtils.d("COOKIES保存", cookieStr);

        }
        return resp;
    }
}
