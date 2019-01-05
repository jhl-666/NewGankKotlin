package com.ljhdemo.newgank.common.http;

import android.app.ProgressDialog;
import android.content.Context;

import com.ljhdemo.newgank.common.R;
import com.ljhdemo.newgank.common.base.BaseApplication;
import com.ljhdemo.newgank.common.http.MsgException;

import com.ljhdemo.newgank.common.utils.LoadingDialog;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

//实现Observer类，把里面用不到的方法这里全处理掉 我们只处理结果 成功/失败
public abstract class RxObserver<T> implements Observer<T> {
    private Context mContext;
    private ProgressDialog dialog;
    private String msg;
    private boolean isShow = false;

    public RxObserver() {

    }

    public RxObserver(Context context, boolean isShow) {
        this.mContext = context;
        this.isShow = isShow;
        msg = mContext.getString(R.string.lib_common_dialog_message_loading);
    }


    public RxObserver(Context context, int msgResId) {
        this.mContext = context;
        isShow = true;
        msg = mContext.getString(msgResId);
    }


    @Override
    public void onComplete() {
        if (isShow) {
            LoadingDialog.cancel();
            /*if (dialog != null)
                dialog.dismiss();*/
        }
    }

    @Override
    public void onSubscribe(@NonNull final Disposable d) {
        if (isShow) {
            /*dialog = new ProgressDialog(mContext);
            dialog.setMessage(msg);
            dialog.setIndeterminate(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    // 对话框取消 直接停止执行请求
                    if (!d.isDisposed()) {
                        d.dispose();
                    }
                }
            });
            dialog.show();*/
            LoadingDialog.show(mContext);
        }
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof MsgException) {
            _onError(e.getMessage());
        } else if (e instanceof ArithmeticException) {
            _onError("登录过期，请重新登录");
        } else {
            _onError(BaseApplication.getContext().getString(R.string.lib_common_error_network_or_server));
        }
        if (isShow) {
            LoadingDialog.cancel();
            /*if (dialog != null)
                dialog.dismiss();*/
        }
    }

    //成功
    protected abstract void _onNext(T t);

    //失败
    protected abstract void _onError(String message);
}