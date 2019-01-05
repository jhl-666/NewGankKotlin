package com.ljhdemo.newgank.common.http;

/**
 * Created by Mr.Hao on 2018/9/2.
 */

public class ApiConfig {
    private static boolean isOnline = true;
    // todo 换上线上地址
    private static final String BASE_URL = "http://jzemotion.xiaoyuqinggan.com/api/mobile/v1/";
    private static final String BASE_URL_DEBUG = "http://172.16.100.24/api/mobile/v1/";//测试地址
    //private static final String BASE_URL_DEBUG = "http://xiaoyu-server/api/mobile/v1/";//测试地址

    public static String getBaseUrl() {
        return isOnline ? BASE_URL : BASE_URL_DEBUG;
    }
}
