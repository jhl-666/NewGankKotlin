package com.ljh.newgank_kotlin.app

import com.ljhdemo.newgank.common.base.BaseApplication
import com.ljhdemo.newgank.common.http.TokenInterceptor
import okhttp3.OkHttpClient

class MyApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun getOkClient(): OkHttpClient.Builder {
        var client = OkHttpClient.Builder()
        client.addInterceptor(TokenInterceptor())
        return client
    }
}