package com.example.abcdialogue.Weibo

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.os.PersistableBundle
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.abcdialogue.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : AppCompatActivity() {
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
        lifecycleScope.launch {
            delay(2000)
            next()
        }
//        handler.postDelayed(object : Runnable {
//            override fun run() {
//                handler.sendEmptyMessage(0)
//            }
//        }, 2000)

    }

    //当activity被意外销毁时调用的方法，我们可以把一些数据保存到Bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    //当activity被意外销毁重建时，调用的方法，先执行的onCreate然后调用的这个方法
    //可以在里面进行数据恢复相关的工作
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }


}