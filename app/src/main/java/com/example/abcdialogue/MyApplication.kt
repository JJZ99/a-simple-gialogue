package com.example.abcdialogue

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.facebook.drawee.backends.pipeline.Fresco
import android.graphics.Typeface
import android.util.Log
import com.example.abcdialogue.Weibo.Util.Net.RetrofitHelper
import com.example.abcdialogue.Weibo.Util.SPUtils.SPFontScale
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.gyf.immersionbar.ImmersionBar


class MyApplication : Application() {
    var count = 1
    var typeFace: Typeface? = null
    val TAG = "MyApplication"



    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        var currFontSize = 1f
        const val REC = "Resources"
        const val CON = "Context"
        var immersionBar: ImmersionBar? = null

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

//        Log.i(TAG,this.toString())
//        Log.i(TAG,this.baseContext.toString())
//        Log.i(TAG,this.applicationContext.toString())



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
        val config: Configuration = res.configuration
        if (currFontSize > 0.5) { //防止第一次获取SP时得到默认值0
            config.fontScale = currFontSize //设置正常字体大小的倍数
        }
        val displayMetrics = res.displayMetrics
        displayMetrics.scaledDensity = displayMetrics.density * config.fontScale

        res.updateConfiguration(config, res.displayMetrics)
        if (count == 1) {
            Log.i(TAG + MyApplication.CON, this.baseContext.toString())

            Log.i(TAG + MyApplication.REC, res.toString())
            --count
        }
        return res
    }
}
