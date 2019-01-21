package com.ljh.newgank_kotlin.ui.activity

import android.os.Bundle
import android.support.annotation.NonNull
import android.util.Log
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.SpeedCalculator
import com.liulishuo.okdownload.core.Util
import com.liulishuo.okdownload.core.breakpoint.BlockInfo
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.listener.DownloadListener4WithSpeed
import com.liulishuo.okdownload.core.listener.assist.Listener4SpeedAssistExtend
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

        task.enqueue(object : DownloadListener4WithSpeed() {
            override fun taskStart(task: DownloadTask) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun blockEnd(task: DownloadTask, blockIndex: Int, info: BlockInfo?, blockSpeed: SpeedCalculator) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun taskEnd(
                task: DownloadTask,
                cause: EndCause,
                realCause: Exception?,
                taskSpeed: SpeedCalculator
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun connectEnd(
                task: DownloadTask,
                blockIndex: Int,
                responseCode: Int,
                responseHeaderFields: MutableMap<String, MutableList<String>>
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun connectStart(
                task: DownloadTask,
                blockIndex: Int,
                requestHeaderFields: MutableMap<String, MutableList<String>>
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun progressBlock(
                task: DownloadTask,
                blockIndex: Int,
                currentBlockOffset: Long,
                blockSpeed: SpeedCalculator
            ) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            private var totalLength: Long = 0
            private var readableTotalLength: String? = null

            override fun infoReady(
                @NonNull task: DownloadTask, @NonNull info: BreakpointInfo,
                fromBreakpoint: Boolean,
                @NonNull model: Listener4SpeedAssistExtend.Listener4SpeedModel
            ) {
                totalLength = info.totalLength
                readableTotalLength = Util.humanReadableBytes(totalLength, true)
               // DemoUtil.calcProgressToView(progressBar, info.totalOffset, totalLength)
            }

            override fun progress(
                @NonNull task: DownloadTask, currentOffset: Long,
                @NonNull taskSpeed: SpeedCalculator
            ) {
                val readableOffset = Util.humanReadableBytes(currentOffset, true)
                val progressStatus = readableOffset + "/" + readableTotalLength
                val speed = taskSpeed.speed()
                val progressStatusWithSpeed = progressStatus + "(" + speed + ")"

                Log.e("tag","当前进度=${(currentOffset *100)/totalLength}")
            }
        })
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
