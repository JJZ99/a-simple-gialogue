package com.example.abcdialogue

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.btn
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.placeHolder

class ConstrainLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constrain_layout4placeholder)
        btn.setOnClickListener {
            placeHolder.setContentId(R.id.textPlaceHolder)
        }

    }
}