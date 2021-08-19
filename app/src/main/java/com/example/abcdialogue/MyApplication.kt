package com.example.abcdialogue

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.example.abcdialogue.Weibo.Util.Net.RetrofitHelper
import com.facebook.drawee.backends.pipeline.Fresco
import android.graphics.Typeface
import android.os.Build

class MyApplication : Application() {
    var typeFace: Typeface? = null

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
    override fun onCreate() {
        super.onCreate()
        //在app启动创建时调用
        //setTypeface()
        RetrofitHelper.init()
        Fresco.initialize(this)
        context = applicationContext
    }
    /**
     * 通过反射方法设置app全局字体
     */
    fun setTypeface() {
        typeFace = Typeface.createFromAsset(assets, "siyuan_normal.otf")
        try {
            val field = Typeface::class.java.getDeclaredField("MONOSPACE")
            field.isAccessible = true
            field.set(null, typeFace)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

}