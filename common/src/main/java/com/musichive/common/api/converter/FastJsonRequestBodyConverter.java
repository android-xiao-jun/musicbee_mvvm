package com.musichive.common.api.converter;

import com.alibaba.fastjson.JSON;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * @Author Jun
 * Date 2021 年 05月 28 日 16:12
 * Description 音乐蜜蜂-mvvm版本
 */
public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    @Override
    public RequestBody convert(T value) throws IOException {

        byte[] bytes = JSON.toJSONBytes(value);

//        String str = (String) JSON.toJSONString(value);

        return RequestBody.create(MEDIA_TYPE, bytes);
    }
}
