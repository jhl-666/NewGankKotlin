package com.ljhdemo.newgank.common.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.ljhdemo.newgank.common.base.BaseApplication;

public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = BaseApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕宽度
     * @returnx
     */
    public static float getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) BaseApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     * @return
     */
    public static float getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) BaseApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
}  