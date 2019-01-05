package com.ljhdemo.newgank.common.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.stetho.Stetho;
import com.ljhdemo.newgank.common.BuildConfig;
import com.ljhdemo.newgank.common.R;
import com.ljhdemo.newgank.common.utils.LogUtil;
import com.ljhdemo.newgank.common.widget.x5webview.PreLoadX5Service;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import okhttp3.OkHttpClient;

/**
 * Created by ljh on 2018/6/4.
 */

public abstract class BaseApplication extends Application {
    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.libCommonThemeColor, android.R.color.white);//全局设置主题颜色
                return new DeliveryHeader(context);
                //return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            Stetho.initializeWithDefaults(this);
        }
        ARouter.init(this);

        mContext = getApplicationContext();
        mClient = getOkClient();
        initLogger();//初始化logger
        //initTencentBrowser();
    }

    private static OkHttpClient.Builder mClient = null;
    public static boolean isTutor = false;//是否是导师

    public abstract OkHttpClient.Builder getOkClient();

    public static OkHttpClient.Builder getClient() {
        return mClient;
    }

    private void initTencentBrowser() {
        Intent intent = new Intent(this, PreLoadX5Service.class);
        startService(intent);
    }

    private void initLogger() {
        LogUtil.init();
    }

    public static Context getContext() {
        return mContext;
    }
}
