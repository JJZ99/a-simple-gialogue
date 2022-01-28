package com.example.abcdialogue

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastError
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject

class TestJson {

}
object T {
    @JvmStatic
    fun main(args: Array<String>) {
        val n = 100000000
        val s1: String = "213"
        val s2: String = "323"
        val s3: String = "435"
        val s4: String = "545"
        val s5: String = "657"
        val s6: String = "65fsdf7"
        val s7: String = "9898"
        val s8: String = "092"
        val s9: String = "092"

        var t = System.currentTimeMillis()
        for (i in 0 until n) {
            foo()
        }
        System.err.println(System.currentTimeMillis() - t)
        t = System.currentTimeMillis()
        for (i in 0 until n) {
            baz(s1, s2, s3, s4, s5, s6, s7, s8, s9)
        }
        System.err.println(System.currentTimeMillis() - t)
        t = System.currentTimeMillis()
        for (i in 0 until n) {
            bar(s1, s2, s3, s4, s5, s6, s7, s8, s9)
        }
        System.err.println(System.currentTimeMillis() - t)
    }

    fun foo() {}
    fun baz(vararg a: String?) {}
    fun bar(a1: String?, a2: String?, a3: String?, a4: String?, a5: String?, a6: String?, a7: String?, a8: String?, a9: String?) {}
}
fun main() {
    var sa = arrayOf("aaa","vvv","ccc","ggg")
    var sb = arrayOf("vvv","ccc","ggg","aaa")
    var sc = arrayOf("ccc","ggg","aaa","vvv")
    var sd = arrayOf("ggg","aaa","vvv","ccc")



    var myMap = mutableMapOf<String,Array<String>>().apply {
        put("1234",sa)
        put("2222",sb)
        put("3333",sc)
        put("4444",sd)
        put("5555",sc)
    }
    var cloneMap = mutableMapOf<String,Array<String>>().apply {
        putAll(myMap)
    }
    var cloneMab2 = cloneMap
    sa[2] = "222"
    println(myMap)
    println(cloneMap)
    println(myMap==cloneMap)
    println(myMap===cloneMap)
    println(cloneMab2 === cloneMap)

    var a = 15
    //方法2
    for (i in 1..a){
        println(i)

    }
}