package com.example.abcdialogue.Module

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.ToastUtil.toast
import com.example.abcdialogue.Util.ToastUtil.toastShort
import kotlinx.android.synthetic.main.activity_main.login_btn
import kotlinx.android.synthetic.main.activity_main.password_input
import kotlinx.android.synthetic.main.activity_main.register_btn
import kotlinx.android.synthetic.main.activity_main.username_input
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var username = username_input.text.toString()
        val password = password_input.text.toString()
        if (checkPhoneNum(username)){

        }
        baseContext.toast("dasdas",)
        username.toastShort(this.applicationContext)


        login_btn.setOnClickListener {

        }
        register_btn.setOnClickListener {

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
}