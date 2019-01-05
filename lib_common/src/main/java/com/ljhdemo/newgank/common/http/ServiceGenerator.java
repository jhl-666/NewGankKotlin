package com.ljhdemo.newgank.common.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.ljhdemo.newgank.common.base.BaseApplication;
import com.ljhdemo.newgank.common.utils.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;

    public static <T> T getApiService(Class<T> serviceClass) {

        if (retrofit == null) {
            synchronized (ServiceGenerator.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl(ApiConfig.getBaseUrl())
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(defaultOkHttpClient())
                            .build();
                }
            }
        }
        return retrofit.create(serviceClass);
    }

    public static OkHttpClient defaultOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (ServiceGenerator.class) {
                if (okHttpClient == null) {
                    OkHttpClient.Builder client;
                    if (null != BaseApplication.getClient()) {//添加拦截器
                        client = BaseApplication.getClient();
                    } else {
                        client = new OkHttpClient.Builder();
                        client.addInterceptor(new TokenInterceptor());
                    }
                    client.writeTimeout(30 * 1000, TimeUnit.MILLISECONDS);
                    client.readTimeout(20 * 1000, TimeUnit.MILLISECONDS);
                    client.connectTimeout(15 * 1000, TimeUnit.MILLISECONDS);

                    //设置缓存路径
                    File httpCacheDirectory = new File(BaseApplication.getContext().getCacheDir(), "okHttpCache");
                    //设置缓存 10M
                    Cache cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
                    client.cache(cache);

                    //失败重连
                    client.retryOnConnectionFailure(true);
                    //设置拦截器
                    client.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
                    client.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);

                    if (com.ljhdemo.newgank.common.BuildConfig.DEBUG)
                        client.addNetworkInterceptor(new StethoInterceptor());

                    okHttpClient = client.build();
                }
            }
        }

        return okHttpClient;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //方案一：有网和没有网都是先读缓存
//                Request request = chain.request();
//                Log.i(TAG, "request=" + request);
//                Response response = chain.proceed(request);
//                Log.i(TAG, "response=" + response);
//
//                String cacheControl = request.cacheControl().toString();
//                if (TextUtils.isEmpty(cacheControl)) {
//                    cacheControl = "public, max-age=60";
//                }
//                return response.newBuilder()
//                        .header("Cache-Control", cacheControl)
//                        .removeHeader("Pragma")
//                        .build();

            //方案二：无网读缓存，有网根据过期时间重新请求
            boolean netWorkConection = NetworkUtils.hasNetWorkConection(BaseApplication.getContext());
            Request request = chain.request();
            if (!netWorkConection) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }

            Response response = chain.proceed(request);
            if (netWorkConection) {
                int maxAge = request.cacheControl().maxAgeSeconds();//有网缓存时长
                if (maxAge > 1) {
                    //LogUtil.d("maxAge: "+maxAge);
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    response = response.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Cache-Control")
                            .header("Cache-Control", "no-cache,no-store")
                            .build();
                }
            } else {
                int maxStale = 60 * 60 * 24 * 7;
                response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    };
}