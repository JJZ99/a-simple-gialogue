package com.example.abcdialogue.Util

import android.content.Context
import android.util.Xml
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.abcdialogue.MyApplication
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.Objects

object Util {
    /**
     * 显示短时间
     */
    fun Any.toastShort(context: Context = MyApplication.context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }
    /**
     * 显示长时间
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