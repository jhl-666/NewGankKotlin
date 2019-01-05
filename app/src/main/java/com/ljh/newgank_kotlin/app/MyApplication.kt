package com.ljh.newgank_kotlin.app

import com.ljh.newgank_kotlin.http.MainTokenInterceptor
import com.ljhdemo.newgank.common.base.BaseApplication
import okhttp3.OkHttpClient

class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun getOkClient(): OkHttpClient.Builder {
        var client = OkHttpClient.Builder()
        client.addInterceptor(MainTokenInterceptor())
        return client
    }
}