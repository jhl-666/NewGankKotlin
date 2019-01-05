package com.ljhdemo.newgank.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import com.ljhdemo.newgank.common.base.BaseApplication;


import java.util.List;

/**
 * 系统相关工具类 <br/>
 * 1）版本判断 <br/>
 * 2）当前应用版本名/版本号获取 <br/>
 * 3）系统设置相关<br/>
 */
public class SystemUtils {
    /**
     * @return 系统版本是否高于Android 4.2（API 17）
     */
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * @return 系统版本是否高于Android 4.3（API 18）
     */
    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * @return 系统版本是否高于Android 4.4（API 19）
     */
    public static boolean isKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    /**
     * @return 系统版本是否高于Android 5.0（API 21）
     */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * @return 系统版本是否高于Android 6.0（API 23）
     */
    public static boolean isM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * @return 系统版本是否高于Android 7.0（API 24）
     */
    public static boolean isN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    /**
     * @return 系统版本是否高于Android 8.0（API 26）
     */
    public static boolean isO() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

    /**
     * @return 系统版本是否高于Android 9.0（API 28）
     */
    public static boolean isP() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P;
    }

    /**
     * @return 获取当前应用版本名
     */
    public static String getVersionName() {
        Context context = BaseApplication.getContext();
        PackageManager packageManager = context.getPackageManager();
        String defaultVersionName = "1.0.0"; // 如更改，为当前更改版本的版本名
        try {
            PackageInfo packageInfo =
                    packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            String versionName = packageInfo.versionName;
            return TextUtils.isEmpty(versionName) ? defaultVersionName : versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // log in file
        }
        return defaultVersionName;
    }

    /**
     * @return 获取当前应用版本号
     */
    public static String getVersionCode() {
        Context context = BaseApplication.getContext();
        PackageManager packageManager = context.getPackageManager();
        String defaultVersionCode = "100"; // 如更改，为上一发布版本的版本号
        try {
            PackageInfo packageInfo =
                    packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            long versionCode = packageInfo.getLongVersionCode();
            return versionCode > 0 ? String.valueOf(versionCode) : defaultVersionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // log in file
        }
        return defaultVersionCode;
    }

    /**
     * @return 是否有修改系统设置的权限
     */
    public static boolean canWriteSystemSettings() {
        if (isM()) {
            try {
                return Settings.System.canWrite(BaseApplication.getContext());
            } catch (Exception e) {
                // log in file
            }
        }
        return true;
    }

    /**
     * 判断service是否已经运行
     *
     * @param className Service的全名，例如PushService.class.getName()
     * @return true:Service已运行，false:Service未运行
     */
    public static boolean isServiceExisted(String className) {
        Context context = BaseApplication.getContext();
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> serviceList = am.getRunningServices(Integer.MAX_VALUE);
            int myUid = android.os.Process.myUid();
            for (ActivityManager.RunningServiceInfo runningServiceInfo : serviceList) {
                if (runningServiceInfo.uid == myUid && runningServiceInfo.service.getClassName()
                        .equals(className)) {
                    return true;
                }
            }
        } catch (Exception e) {
            // log in file
        }
        return false;
    }
}
