package com.ljhdemo.newgank.common.utils;

import android.app.Activity;

import com.tbruyelle.rxpermissions2.RxPermissions;

public class PermissionUtil {
    private RxPermissions rxPermissions;

    private void PermissionUtil() {

    }

    public static PermissionUtil getInstance() {
        return Holder.permissionUtil;
    }

    private static class Holder {
        public static final PermissionUtil permissionUtil = new PermissionUtil();
    }

    public RxPermissions getRxPermissions(Activity activity) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(activity);
        }
        return rxPermissions;
    }
}
