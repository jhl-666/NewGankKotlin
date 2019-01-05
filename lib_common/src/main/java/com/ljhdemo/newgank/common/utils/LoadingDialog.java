package com.ljhdemo.newgank.common.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by ljh on 2016/11/21.
 * 加载中Dialog
 */

public class LoadingDialog {

    private static KProgressHUD kProgressHUD;

    private static void create(Context context) {
        if (kProgressHUD == null) {
            kProgressHUD = KProgressHUD.create(context)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    //.setLabel("加载中")
                    //.setDetailsLabel("Downloading data")
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.4f);
        }
    }

    public static void show(Context context) {
        create(context);
        if (kProgressHUD != null) {
            kProgressHUD.show();
        }
    }

    public static void cancel() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
            kProgressHUD = null;
        }
    }
}