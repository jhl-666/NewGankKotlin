package com.ljhdemo.newgank.common.base;

import android.os.Bundle;

/**
 * Created by ljh on 2017/3/10.
 */

public abstract class MVPBaseActivity<V, T extends BasePresenter<V>> extends BaseActivity {
    protected T mPresenter;//Presenter 对象

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter = createPresenter(); //创建Presenter

        mPresenter.attachView((V) this);
    }


    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    protected abstract T createPresenter();
}
