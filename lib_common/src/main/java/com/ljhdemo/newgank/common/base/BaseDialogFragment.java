package com.ljhdemo.newgank.common.base;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.WindowManager;

import com.ljhdemo.newgank.common.R;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;

public class BaseDialogFragment extends RxDialogFragment {

    @NonNull
    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, R.style.LibCommonBottomDialog);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        WindowManager.LayoutParams params = getDialog().getWindow()
                .getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow()
                .setAttributes(params);
    }

    public void show(FragmentManager manager) {
        if (manager == null) return;
        if (this.isAdded()) {
            return;
        }

        manager.beginTransaction()
                .add(this, getClass().getName())
                .commitAllowingStateLoss();
    }

    /**
     * Can not perform this action after onSaveInstanceState
     */
    public void show(AppCompatActivity activity) {
        if (activity == null || activity.isFinishing()) return;
        if (!isAdded()) {
            if (activity instanceof BaseActivity) {
                BaseActivity baseActivity = (BaseActivity) activity;
                if (baseActivity.isOnPause) return;
            }
            show(activity.getSupportFragmentManager());
        }
    }
}