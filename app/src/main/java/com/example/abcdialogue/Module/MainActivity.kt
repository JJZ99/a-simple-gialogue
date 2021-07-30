package com.example.abcdialogue.Module

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.Util.toast
import com.example.abcdialogue.Util.Util.toastShort
import kotlinx.android.synthetic.main.activity_main.login_btn
import kotlinx.android.synthetic.main.activity_main.password_input
import kotlinx.android.synthetic.main.activity_main.register_btn
import kotlinx.android.synthetic.main.activity_main.school
import kotlinx.android.synthetic.main.activity_main.tell
import kotlinx.android.synthetic.main.activity_main.username_input
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //if (checkPhoneNum(username)){

        //}
        baseContext.toast("hello",)
        //username.toastShort(this.applicationContext)

        val sharedPref = this.getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        login_btn.setOnClickListener {

            val username = username_input.text.toString()
            val password = password_input.text.toString()
            val tell = tell.text.toString()
            val school = school.text.toString()
            Log.i("info","$username+$password+$tell+$school")
            if (username.isEmpty() || password.isEmpty() || school.isEmpty() || tell.isEmpty()
            ) {
                "你能不能输入了再点啊！".toastShort(this)
                return@setOnClickListener;
            }
            if (!checkPhoneNum(tell)){
                "你能不能输入电话啊！".toastShort(this)

                return@setOnClickListener
            }

            var edit = sharedPref.edit()
            edit.putString("username",username)
            edit.putString("password",password)
            edit.putLong("tell", tell.toLong())
            edit.putString("school",school)
            edit.commit()
            var intent = Intent(this, MainActivity2().javaClass)
            startActivity(intent)
            "jump！jump！".toastShort(this)


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