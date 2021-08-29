package com.example.abcdialogue.Weibo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.WindowManager
import com.example.abcdialogue.R

class StartActivity : AppCompatActivity() {
    //向主进程发送消息的句柄
    var handler: Handler = object : Handler(Looper.getMainLooper()){
        //子类必须实现此功能才能接收消息
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            next()
        }
    }

    /**
     * 自定义方法
     * 启动登录界面
     */
    fun next() {
        var intent = Intent(this,LoginActivity().javaClass)
        startActivity(intent);
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //去掉标题栏
       // supportActionBar?.hide()
        setContentView(R.layout.activity_start)
        //去掉状态栏
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //延时2秒发送一个消息给主进程,让主进程执行next()方法,跳到登陆界面
        handler.postDelayed(object : Runnable {
            override fun run() {
                handler.sendEmptyMessage(0)
            }
        }, 2000)

    }
}