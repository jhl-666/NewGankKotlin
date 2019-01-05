package com.ljh.newgank_kotlin.util

class SingleInstance private constructor() {
    companion object {
        val instance = Holder.singleInstance
    }

    private object Holder {
        val singleInstance = SingleInstance()
    }

    fun sum(a: Int, b: Int): Int {
        return a + b
    }
}