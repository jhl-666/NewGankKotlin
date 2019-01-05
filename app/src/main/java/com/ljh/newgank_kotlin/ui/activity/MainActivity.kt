package com.ljh.newgank_kotlin.ui.activity

import android.os.Bundle
import com.ljh.newgank_kotlin.R
import com.ljh.newgank_kotlin.util.SingleInstance
import com.ljhdemo.newgank.common.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun provideLayoutId(savedInstanceState: Bundle?): Int {

        return R.layout.activity_main
    }

    override fun initView(savedInstanceState: Bundle?) {
        var sum = SingleInstance.instance.sum(3,5)
    }
}
