package com.ljhdemo.newgank.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.ljhdemo.newgank.common.R;
import com.ljhdemo.newgank.common.base.BaseApplication;

/**
 * 统一使用ToastUtils
 */
public class ToastUtils {
    private static Handler mHandler = new Handler(Looper.getMainLooper());
    private static Toast mToast;

    /**
     * 普通文字Toast
     */
    public static void showMessage(String msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    public static void showMessage(@StringRes int msg) {
        showMessage(UIUtil.getString(msg), Toast.LENGTH_SHORT);
    }

    public static void showMessage(String msg, int duration) {
        showMessageInternal(msg, null, duration);
    }

    public static void showMessage(View customView) {
        showMessageInternal(null, customView, Toast.LENGTH_SHORT);
    }

    private static void showMessageInternal(@Nullable final String msg, @Nullable final View customView,
                                            final int duration) {
        if (TextUtils.isEmpty(msg) && customView == null) {
            return;
        }

        final Context context = BaseApplication.getContext();
        final View toastView = customView == null ? createDefaultToastView(context, msg) : customView;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                try {
                    mToast = createToast(context, toastView, duration);
                    mToast.show();
                } catch (Exception e) {
                }
            }
        });
    }

    private static View createDefaultToastView(Context context, String msg) {
        @SuppressLint("InflateParams") View toastView = LayoutInflater.from(context)
                .inflate(R.layout.lib_common_toast_text_widget, null);
        TextView textView = (TextView) toastView.findViewById(R.id.text);
        textView.setText(msg);
        return toastView;
    }

    private static Toast createToast(Context context, @NonNull View view, int duration) {
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(duration);
        return toast;
    }

    public static void showMessageCenter(@Nullable final View customView) {
        if (customView == null) {
            return;
        }

        final Context context = BaseApplication.getContext();

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Toast toast = createToast(context, customView, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                } catch (Exception e) {
                }
            }
        });
    }
}
