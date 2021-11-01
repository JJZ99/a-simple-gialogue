package com.example.abcdialogue

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.example.abcdialogue.Weibo.Util.Net.RetrofitHelper
import com.facebook.drawee.backends.pipeline.Fresco
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.abcdialogue.Weibo.Util.SPUtils.SPFontScale
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.example.abcdialogue.Weibo.SystemObserver


class MyApplication : Application() {
    var typeFace: Typeface? = null


    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var currFontSize = 1f
    }
    override fun onCreate() {
        super.onCreate()
        //在app启动创建时调用
        //setTypeface()
        RetrofitHelper.init()
        Fresco.initialize(this)
        context = applicationContext
        val sharedPref = this.getSharedPreferences(
            getString(R.string.sp_font_scale), Context.MODE_PRIVATE)
        currFontSize = sharedPref.getFloat(SPFontScale, 1F)
        "获取：$currFontSize".toastInfo()
        Log.i("获取currFontSize",currFontSize.toString())

        //这里我想监听系统字体的变化，但还不会
//        SystemObserver(Handler(Looper.myLooper()), context).apply {
//            context.contentResolver.registerContentObserver(fontScaleUri, true, this)
//        }

    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
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

    override fun getResources(): Resources {
        val res: Resources = super.getResources()

        val config: Configuration = res.getConfiguration()
        if (currFontSize > 0.5) { //防止第一次获取SP时得到默认值0
            config.fontScale = currFontSize //设置正常字体大小的倍数
        }


       // this.createConfigurationContext(config)
       res.updateConfiguration(config, res.displayMetrics)
        return res
//        val resources = super.getResources()
//        val conf = resources.configuration
//        val displayMetrics = resources.displayMetrics
//        conf.fontScale = mFontScale
//        conf.densityDpi = getDefaultDisplayDensity()
//        displayMetrics.scaledDensity = displayMetrics.density * conf.fontScale
//        resources.updateConfiguration(conf, displayMetrics)
//        return resources
    }

}
