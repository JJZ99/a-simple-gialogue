package com.example.abcdialogue.Module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.helloWorld
import kotlinx.android.synthetic.main.activity_main2.image_btn
import kotlinx.android.synthetic.main.activity_main2.read_btn
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Util.Util.toastInfo
import com.example.abcdialogue.Weibo.Util.FrescoUtil


class MainActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main2)
        read_btn.setOnClickListener {
            val sharedPref = this.getSharedPreferences(
                getString(com.example.abcdialogue.R.string.preference_file_key), Context.MODE_PRIVATE)
            val edit = sharedPref.edit()
            val username = sharedPref.getString("username","JJZ")
            val password = sharedPref.getString("password","123456")
            val age = sharedPref.getLong("tell",3)
            val school = sharedPref.getString("school","希望小学")
            helloWorld.text = "$username\n$password\n$age\n$school"
            "$username\n$password\n$age\n$school".toastInfo()
        }
        image_btn.setOnClickListener {
            val url="https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"
            FrescoUtil.loadImage(findViewById(R.id.net_image),url)
        }
    }
}