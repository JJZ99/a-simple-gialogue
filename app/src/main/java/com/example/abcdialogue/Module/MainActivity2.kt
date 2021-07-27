package com.example.abcdialogue.Module

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.Util.toastShort
import kotlinx.android.synthetic.main.activity_main2.helloWorld
import kotlinx.android.synthetic.main.activity_main2.read_btn

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        read_btn.setOnClickListener {
            val sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE)
            val edit = sharedPref.edit()
            val username = sharedPref.getString("username","JJZ")
            val password = sharedPref.getString("password","123456")
            val age = sharedPref.getLong("tell",3)
            val school = sharedPref.getString("school","希望小学")
            helloWorld.text = "$username\n$password\n$age\n$school"
            "$username\n$password\n$age\n$school".toastShort(this)
        }
    }
}