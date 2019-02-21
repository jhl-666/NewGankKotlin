package com.ljh.newgank_kotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import com.liulishuo.okdownload.DownloadTask
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
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {
    override fun provideLayoutId(savedInstanceState: Bundle?): Int {

        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        var sum = SingleInstance.instance.sum(3, 5)
        val filename = "single-test"
        val url = "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk"
        var task = DownloadTask.Builder(url, cacheDir)
            .setFilename(filename)
            // the minimal interval millisecond for callback progress
            .setMinIntervalMillisCallbackProcess(16)
            // ignore the same task has already completed in the past.
            .setPassIfAlreadyCompleted(false)
            .build()

        rvBtn.setOnClickListener { startActivity(Intent(this,RecyclerViewActivity::class.java)) }
        animationBtn.setOnClickListener { startActivity(Intent(this,AnimationActivity::class.java)) }
        flexBoxBtn.setOnClickListener {  startActivity(Intent(this,FlexBoxActivity::class.java))}
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
