package com.example.abcdialogue.Weibo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.abcdialogue.Module.MainActivity2
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.Util.toast
import com.example.abcdialogue.Util.Util.toastInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.util.regex.Pattern

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        baseContext.toast("hello",)

        login_btn.setOnClickListener {

            var intent = Intent(this, MainActivity2().javaClass)
            startActivity(intent)
            "jump！jump！".toastInfo()
        }
        wei_bo_btn.setOnClickListener {
            val sharedPref = this.getSharedPreferences(
                getString(R.string.sp_access_token), Context.MODE_PRIVATE)
            val token = sharedPref.getString(ACCESS_TOKEN,"")
            //如果token为空就跳转登陆获取
            if(token.isNullOrEmpty()){
                "不存在Token：${token}跳转到登陆授权界面".toastInfo()
                var intent = Intent(this, InitSDK().javaClass)
                startActivity(intent)
            }else{
                //不为空直接跳转到微博页
                "已经存在Token：${token}直接跳到微博".toastInfo()
                Log.i("token has",token)
                InitSDK.TOKEN = token
                var intent = Intent(this, WeiBoActivity().javaClass)
                startActivity(intent)
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

    companion object {
        const val ACCESS_TOKEN = "access_token"
    }
}