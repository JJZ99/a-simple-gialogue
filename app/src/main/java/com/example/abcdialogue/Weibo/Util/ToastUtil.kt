package com.example.abcdialogue.Weibo.Util

import android.content.Context
import android.widget.Toast
import com.example.abcdialogue.MyApplication
import es.dmoral.toasty.Toasty

object ToastUtil {
    /**
     * 五种toast显示，分别是错误，成功 信息 警告 和正常。短显示
     */
    fun Any.toastError() {
        Toasty.error(MyApplication.context, this.toString(),Toast.LENGTH_SHORT, true).show()
    }
    fun Any.toastSuccess() {
        Toasty.success(MyApplication.context, this.toString(),Toast.LENGTH_SHORT, true).show()
    }
    fun Any.toastInfo() {
        Toasty.info(MyApplication.context, this.toString(),Toast.LENGTH_SHORT, true).show()
    }
    fun Any.toastWarning() {
        Toasty.warning(MyApplication.context, this.toString(),Toast.LENGTH_SHORT, true).show()
    }
    fun Any.toastNormal() {
        Toasty.normal(MyApplication.context, this.toString(),Toast.LENGTH_SHORT).show()
    }

    /**
     * 显示长时间，不太用。。。
     */
    fun Any.toastLong(context: Context = MyApplication.context, duration: Int = Toast.LENGTH_LONG): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }

    //或者也可以这样，用Context调用
    fun Context.toast(content: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, content, duration).apply {
            show()
        }
    }


}