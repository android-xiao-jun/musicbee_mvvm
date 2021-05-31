package com.musichive.common.api.interceptor;

import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.musichive.common.config.HttpConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.ByteString;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 16:38
 * Description 音乐蜜蜂-mvvm版本
 */
public class SignatureInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        return chain.proceed(addSignature(chain, request));
    }

    private Request addSignature(Chain chain, Request request) {
        final String appShortVersion = HttpConstants.getShortAppVersionName();
        final String appName = AppUtils.getAppName();
        Locale locale = Locale.getDefault();
        String language = locale.getLanguage();
        Request.Builder requestBuilder = chain.request().newBuilder();
        String userAgent = HttpConstants.buildUserAgent();
        String sourceAgent = request.header(HttpConstants.HEADER_USER_AGENT);
        requestBuilder.removeHeader(HttpConstants.HEADER_USER_AGENT);
        if (!TextUtils.isEmpty(sourceAgent)) {
            userAgent = userAgent + " " + sourceAgent;
        }
        requestBuilder.addHeader(HttpConstants.HEADER_USER_AGENT, userAgent);
        String token = "";
        if (!TextUtils.isEmpty(token)) {
            requestBuilder.addHeader(HttpConstants.HEADER_AUTHORIZATION, HttpConstants.buildHeader(token));
        }

        String timestamp = String.valueOf(System.currentTimeMillis());
        Map<String, String> commonParams = new HashMap<>();
        commonParams.put(HttpConstants.REQUEST_PARAM_COUNTRY, locale.getCountry());
        commonParams.put(HttpConstants.REQUEST_PARAM_LANGUAGE, language);
        commonParams.put(HttpConstants.REQUEST_PARAM_PLATFORM, String.valueOf(1));
        commonParams.put(HttpConstants.REQUEST_PARAM_APP_VERSION, appShortVersion);
        commonParams.put(HttpConstants.REQUEST_PARAM_OS_VERSION, String.valueOf(Build.VERSION.SDK_INT));
        commonParams.put(HttpConstants.REQUEST_PARAM_DEVICE_NAME, Build.MODEL);
        commonParams.put(HttpConstants.TIMEZONE, TimeZone.getDefault().getID());
        commonParams.put(HttpConstants.REQUEST_PARAM_APP_NAME, appName);
        commonParams.put(HttpConstants.TIMESTAMP, timestamp);
        commonParams.put(HttpConstants.CHANNEL, "音乐蜜蜂 mvvm 架构 测试版");
        Iterator<String> keys = commonParams.keySet().iterator();
        RequestBody requestBody = chain.request().body();
        if (request.method().equalsIgnoreCase("GET")) {
            HttpUrl.Builder urlBuilder = chain.request().url().newBuilder();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = commonParams.get(key);
                urlBuilder.addQueryParameter(key, value);
            }
            HttpUrl finalUrl = urlBuilder.build();

            Map<String, String> params = new HashMap<>();
            for (String key : finalUrl.queryParameterNames()) {
                params.put(key, finalUrl.queryParameter(key));
            }
            String signature = sign(token, params, timestamp);

            requestBuilder.addHeader(HttpConstants.REQUEST_PARAM_SIGNATURE, signature);
            requestBuilder.addHeader(HttpConstants.TIMESTAMP, timestamp);
            finalUrl = urlBuilder.build();

            return requestBuilder.url(finalUrl).build();
        } else {

            FormBody.Builder formBodyBuilder = new FormBody.Builder();

            Map<String, String> signParams = new HashMap<>();
            while (keys.hasNext()) {
                String key = keys.next();
                String value = commonParams.get(key);
                formBodyBuilder.add(key, value);
                signParams.put(key, value);
            }
            RequestBody finalBody = null;

            if (requestBody instanceof FormBody) {

                FormBody requestFormBody = (FormBody) requestBody;
                for (int i = 0; i < requestFormBody.size(); i++) {
                    signParams.put(requestFormBody.name(i), requestFormBody.value(i));
                }
                String signature = sign(token, signParams, timestamp);
                requestBuilder.addHeader(HttpConstants.REQUEST_PARAM_SIGNATURE, signature);
                requestBuilder.addHeader(HttpConstants.TIMESTAMP, timestamp);
                String postBodyString = bodyToString(request.body());
                postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBodyBuilder.build());

                finalBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString);
            } else if (requestBody instanceof MultipartBody) {
                MultipartBody partBody = (MultipartBody) requestBody;
                List<MultipartBody.Part> parts = partBody.parts();
                MultipartBody.Builder partBuilder = new MultipartBody.Builder();
                for (int i = 0; i < parts.size(); i++) {
                    MultipartBody.Part part = parts.get(i);
                    partBuilder.addPart(part);
                }

                String signature = sign(token, signParams, timestamp);
                requestBuilder.addHeader(HttpConstants.REQUEST_PARAM_SIGNATURE, signature);
                requestBuilder.addHeader(HttpConstants.TIMESTAMP, timestamp);
                partBuilder.addPart(formBodyBuilder.build());

                finalBody = partBuilder.build();
            } else {
                if (requestBody != null) {
                    MediaType mediaType = requestBody.contentType();
                    if (mediaType != null && mediaType.type().equals("application") && mediaType.subtype().equals("json")) {
                        String jsonStr = bodyToString(requestBody);
                        JsonParser parser = new JsonParser();
                        JsonObject jsonObject = parser.parse(jsonStr).getAsJsonObject();
                        //add common params
                        for (String key : commonParams.keySet()) {
                            String value = commonParams.get(key);
                            jsonObject.addProperty(key, value);
                        }
                        String finalJsonStr = jsonObject.toString();
                        signParams.clear();
                        signParams.put("body", EncryptUtils.encryptMD5ToString(finalJsonStr).toLowerCase());
                        String signature = sign(token, signParams, timestamp);
                        requestBuilder.addHeader(HttpConstants.REQUEST_PARAM_SIGNATURE, signature);
                        requestBuilder.addHeader(HttpConstants.TIMESTAMP, timestamp);
                        ByteString byteString = ByteString.encodeUtf8(jsonObject.toString());

                        finalBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), byteString);
                    }
                }
                if (finalBody == null) {
                    //暂时不可能走到此处，因为上面的代码有阻止.
                    LogUtils.eTag("elst", "########Custom request body,  you need support here########");
                    String signature = sign(token, signParams, timestamp);
                    requestBuilder.addHeader(HttpConstants.REQUEST_PARAM_SIGNATURE, signature);
                    requestBuilder.addHeader(HttpConstants.TIMESTAMP, timestamp);
                    finalBody = formBodyBuilder.build();
                }
            }
            requestBuilder.removeHeader("Content-Length");
            try {
                requestBuilder.addHeader("Content-Length", String.valueOf(finalBody.contentLength()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (request.method().equalsIgnoreCase("PUT")) {
                return requestBuilder.put(finalBody).build();
            } else if (request.method().equalsIgnoreCase("DELETE")) {
                return requestBuilder.delete(finalBody).build();
            } else if (request.method().equalsIgnoreCase("PATCH")) {
                return requestBuilder.patch(finalBody).build();
            }
            return requestBuilder.method(request.method(), finalBody).build();
        }
    }

    private String sign(String token, Map<String, String> params, String timestamp) {
        ArrayList<String> nameLists = new ArrayList<>(params.keySet());
        Collections.sort(nameLists);

        StringBuilder paramString = new StringBuilder();
        for (int i = 0; i < nameLists.size(); i++) {
            String name = nameLists.get(i);
            String value = params.get(name);
            paramString.append(name).append("=").append(value);
            paramString.append("&");
        }
        if (!TextUtils.isEmpty(token)) {
            paramString.append("token=").append(token);
            paramString.append("&");
        }
        paramString.append(HttpConstants.TIMESTAMP + "=").append(timestamp);
        paramString.append("&secret=b8c3b4ce6dce2513c1c48e2708c66f52");

        return EncryptUtils.encryptMD5ToString(paramString.toString()).toLowerCase();
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}
