package com.ljhdemo.newgank.common.http;

/**
 * Created by Mr.Hao on 2018/9/2.
 */

public class ApiConfig {
    private static boolean isOnline = true;

    private static final String BASE_URL = "http://gank.io/api/";
    private static final String BASE_URL_DEBUG = "00";//测试地址

    public static String getBaseUrl() {
        return isOnline ? BASE_URL : BASE_URL_DEBUG;
    }
}
