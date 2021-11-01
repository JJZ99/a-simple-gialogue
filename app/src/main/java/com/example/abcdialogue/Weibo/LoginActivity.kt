package com.example.abcdialogue.Weibo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.example.abcdialogue.Weibo.Util.ToastUtil.toast
import kotlinx.android.synthetic.main.activity_login.open_weibo_anim
import kotlinx.android.synthetic.main.activity_login.wei_bo_btn
import java.util.regex.Pattern
import com.facebook.drawee.backends.pipeline.Fresco

import com.facebook.drawee.interfaces.DraweeController

import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.abcdialogue.MyApplication.Companion.context

import com.facebook.common.util.UriUtil
import kotlinx.android.synthetic.main.activity_login.open_weibo_anim_constrain
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoginActivity : AppCompatActivity() {

    //向主进程发送消息的句柄
//    var handler: Handler = object : Handler(Looper.getMainLooper()){
//        //子类必须实现此功能才能接收消息
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            next()
//        }
//    }
    /**
     * 自定义方法
     * 启动登录界面
     */
    suspend fun next() {
        val sharedPref = this.getSharedPreferences(
            getString(com.example.abcdialogue.R.string.sp_access_token), Context.MODE_PRIVATE)
        val token = sharedPref.getString(ACCESS_TOKEN,"")
        //如果token为空就跳转登陆获取
        if(token.isNullOrEmpty()){
            //"不存在Token：${token}跳转到登陆授权界面".toastInfo()
            var intent = Intent(this, InitSDK().javaClass)
            startActivity(intent)
            finish()
        }else{
            //不为空直接跳转到微博页
            //"已经存在Token：${token}直接跳到微博".toastInfo()
            Log.i("token has",token)
            //InitSDK.TOKEN = "2.00llrezFRMpNJDd3d5f9f262Ln9WYC"
            InitSDK.TOKEN = token
            var intent = Intent(this, WeiBoActivity().javaClass)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.abcdialogue.R.layout.activity_login)
        //权限申请
        getPermission()
        val uri = Uri.Builder()
            .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
            .path(java.lang.String.valueOf(com.example.abcdialogue.R.drawable.loading))
            .build()
        val controller: DraweeController = Fresco.newDraweeControllerBuilder()
            .setUri(uri)
            .setAutoPlayAnimations(true)
            .build()
        baseContext.toast("Hello")
        wei_bo_btn.setOnClickListener {
            open_weibo_anim_constrain.visibility = View.VISIBLE
            open_weibo_anim.visibility = View.VISIBLE
            open_weibo_anim.controller = controller
            lifecycleScope.launch(){
                delay(2000)
                next()
            }
//            //延时2秒发送一个消息给主进程,让主进程执行next()方法,跳到登陆界面
//            handler.postDelayed(object : Runnable {
//                override fun run() {
//                    handler.sendEmptyMessage(0)
//                }
//            }, 1500)
            wei_bo_btn.isEnabled = false

        }
    }

    /**
     * 判断是否拥有读写权限。没有就申请
     */
    private fun getPermission() {
        //验证权限
        if (Build.VERSION.SDK_INT >= 23) {
            val REQUEST_CODE_CONTACT = 101
            val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
            //验证是否许可权限
            for (str in permissions) {
                if (checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    requestPermissions(permissions, REQUEST_CODE_CONTACT)
                }
            }
        }
    }

    /***
     * 手机号码检测
     */
    fun checkPhoneNum(num: String): Boolean{
        val regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5-9])|(166)|(19[8,9])|)\\d{8}$"
        val p = Pattern.compile(regExp)
        val m = p.matcher(num)
        return m.matches()
    }

    /**
     * 在点击允许或者拒绝后会执行这个方法
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }
}