package com.example.abcdialogue.LearnConstraintLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.abcdialogue.R



class FlyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fly)
        val constraintLayout = findViewById<ConstraintLayout>(R.id.root)
        constraintLayout.loadLayoutDescription(R.xml.constraint_layout_states)
        findViewById<Button>(R.id.wei_bo_btn).setOnClickListener {
        }
    }
}