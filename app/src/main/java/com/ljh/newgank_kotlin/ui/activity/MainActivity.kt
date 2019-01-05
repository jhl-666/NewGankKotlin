package com.ljh.newgank_kotlin.ui.activity

import android.os.Bundle
import com.ljh.newgank_kotlin.R
import com.ljh.newgank_kotlin.http.ApiFactory
import com.ljh.newgank_kotlin.ui.bean.GankResult
import com.ljh.newgank_kotlin.util.SingleInstance
import com.ljhdemo.newgank.common.base.BaseActivity
import com.ljhdemo.newgank.common.utils.ToastUtils
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : BaseActivity() {
    override fun provideLayoutId(savedInstanceState: Bundle?): Int {

        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        var sum = SingleInstance.instance.sum(3,5)
    }

    override fun initData() {
        super.initData()
        ApiFactory.gankApi()
            .getGankResult("拓展资源", 10, 2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(mProvider.bindUntilEvent<GankResult>(ActivityEvent.DESTROY))
            .subscribe(object : Observer<GankResult> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(gankResult: GankResult) {
                    ToastUtils.showMessage("aaaaa")
                }

                override fun onError(e: Throwable) {
                }

                override fun onComplete() {

                }
            })
    }
}
