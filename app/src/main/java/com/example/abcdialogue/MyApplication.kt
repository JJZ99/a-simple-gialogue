package com.example.abcdialogue

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.example.abcdialogue.Weibo.Util.Net.RetrofitHelper
import com.facebook.drawee.backends.pipeline.Fresco

class MyApplication : Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        RetrofitHelper.init()
        Fresco.initialize(this)
        context = applicationContext
    }
}