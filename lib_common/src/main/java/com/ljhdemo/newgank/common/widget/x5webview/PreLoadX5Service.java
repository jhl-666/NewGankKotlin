package com.ljhdemo.newgank.common.widget.x5webview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

public class PreLoadX5Service extends Service {
    public PreLoadX5Service() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initX5();
        preinitX5WebCore();
    }

    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            // TODO Auto-generated method stub
            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            Log.d("app", " onViewInitFinished is " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
            // TODO Auto-generated method stub
        }
    };
    private void initX5() {
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    private void preinitX5WebCore() {
        if (!QbSdk.isTbsCoreInited()) { // preinit只需要调用一次，如果已经完成了初始化，那么就直接构造view
            QbSdk.preInit(getApplicationContext(), cb);// 设置X5初始化完成的回调接口 } }
        }
    }
}
