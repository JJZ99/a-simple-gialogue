package com.example.abcdialogue.Weibo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abcdialogue.MyApplication.Companion.currFontSize
import kotlinx.android.synthetic.main.activity_font_size.tv_font_size1
import kotlinx.android.synthetic.main.activity_font_size.tv_font_size2
import kotlinx.android.synthetic.main.activity_font_size.tv_font_size3
import com.example.abcdialogue.Weibo.Util.DensityUtils
import com.example.abcdialogue.Weibo.Util.SPUtils
import com.example.abcdialogue.Weibo.Util.SPUtils.SPFontScale
import com.example.abcdialogue.baseui.ConfirmDialog

import com.example.abcdialogue.baseui.FontSizeView.OnChangeCallbackListener
import kotlinx.android.synthetic.main.activity_font_size.font_size_tv
import kotlinx.android.synthetic.main.activity_font_size.fsv_font_size
import android.app.AlarmManager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import com.hitomi.tilibrary.utils.AppManager


class FontSizeActivity : AppCompatActivity() {
    var willFontScale = 1F
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.abcdialogue.R.layout.activity_font_size)
        var initialPosition = ((currFontSize - 0.875) / 0.125).toInt()
        fsv_font_size.post {
            fsv_font_size.currentProgress = initialPosition
        }

        //滑动返回监听
        fsv_font_size.setChangeCallbackListener(OnChangeCallbackListener { position ->
            val dimension = resources.getDimensionPixelSize(com.example.abcdialogue.R.dimen.sp_stander)
            //根据position 获取字体倍数
            willFontScale = (0.875 + 0.125 * position).toFloat()
            //放大后的sp单位
            val v: Float = willFontScale * DensityUtils.px2sp(this@FontSizeActivity, dimension.toFloat())
            //改变当前页面大小
            changeTextSize(v)
        })
        font_size_tv.setOnClickListener {
            ConfirmDialog(this).apply {
                leftClickAction = { _, _ ->
                    dismiss()
                }
                rightClickAction = {_, _ ->
                    currFontSize = willFontScale
                  //  SPUtils.put(this@FontSizeActivity, SPFontScale, currFontSize)

                    //获取后将willFontScale存到sp文件，然后跳转到微博页
                    val sharedPref = this@FontSizeActivity.getSharedPreferences(getString(com.example.abcdialogue.R.string.sp_font_scale), Context.MODE_PRIVATE)
                    val edit = sharedPref.edit()
                    edit.putFloat(SPFontScale, willFontScale)
                    edit.commit()
                    "保存：$willFontScale".toastSuccess()
                    Log.i("保存currFontSize",willFontScale.toString())


                    val i = baseContext.packageManager
                        .getLaunchIntentForPackage(baseContext.packageName)
                    i!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(i)

//                    val intent = packageManager
//                        .getLaunchIntentForPackage(application.packageName)
//                    val restartIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)
//
//                    val mgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
//                    mgr[AlarmManager.RTC, System.currentTimeMillis()] =
//                        restartIntent
//                    System.exit(0)

                    //重启应用
                    //.....
                }
            }.show()
        }
    }

    private fun changeTextSize(textSize: Float) {
        tv_font_size1.setTextSize(textSize);
        tv_font_size2.setTextSize(textSize);
        tv_font_size3.setTextSize(textSize);
    }

}