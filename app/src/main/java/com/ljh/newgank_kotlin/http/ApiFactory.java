package com.ljh.newgank_kotlin.http;

import com.ljhdemo.newgank.common.http.ServiceGenerator;

/**
 * Created by Mr.Hao on 2018/10/16.
 */
public enum ApiFactory {
    INSTANCE;

    private static GankApi mGankApi;

    public static GankApi gankApi(){
        return mGankApi = ServiceGenerator.getApiService(GankApi.class);
    }
}
