package com.example.abcdialogue.Util

import android.content.Context
import android.widget.Toast
import java.util.Objects

object ToastUtil {
    /**
     * 显示短时间
     */
    fun Any.toastShort(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
    /**
     * 显示长时间
     */
    fun Any.toastLong(context: Context, duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    //或者也可以这样，用Context调用
    fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, content, duration).apply {
            show()
        }
    }


}