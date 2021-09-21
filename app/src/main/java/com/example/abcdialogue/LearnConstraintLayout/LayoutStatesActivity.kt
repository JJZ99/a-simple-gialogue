package com.example.abcdialogue.LearnConstraintLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.abcdialogue.R
import kotlinx.android.synthetic.main.activity_layout_states.*
import kotlinx.android.synthetic.main.states_content_loading.*

class LayoutStatesActivity : AppCompatActivity() {
    private val constraintLayout: ConstraintLayout by lazy { findViewById<ConstraintLayout>(R.id.constraint_state) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layout_states)
        constraintLayout.loadLayoutDescription(R.xml.constraint_layout_states)

        success.setOnClickListener {
            constraintLayout.setState(R.id.success, 0, 0)
        }
        error.setOnClickListener {
            constraintLayout.setState(R.id.error, 0, 0)
        }
        loading.setOnClickListener {
            constraintLayout.setState(R.id.loading, 0, 0)
        }
    }
}