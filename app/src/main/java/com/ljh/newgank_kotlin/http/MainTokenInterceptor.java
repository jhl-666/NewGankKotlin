package com.ljh.newgank_kotlin.http;


import okhttp3.*;

import java.io.IOException;

/**
 * OkHttp拦截器（判断是否登录过期，并重新登录）
 */
public class MainTokenInterceptor implements Interceptor {

    public MainTokenInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .build();

        Response response = chain.proceed(request);
        MediaType mediaType = response.body().contentType();
        String result = response.body().string();

        return response.newBuilder()
                .body(ResponseBody.create(mediaType, result))//body.string()只能读取一次,读取后重新创建一个返回
                .build();
    }
}