package com.example.abcdialogue.LearnConstraintLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintSet
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import kotlinx.android.synthetic.main.activity_flow.flow
import kotlinx.android.synthetic.main.activity_flow.flowRoot

class FlowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow)
    }
}