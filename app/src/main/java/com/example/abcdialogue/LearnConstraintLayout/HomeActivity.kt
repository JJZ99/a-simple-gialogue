package com.example.abcdialogue.LearnConstraintLayout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.abcdialogue.R
import kotlinx.android.synthetic.main.activity_home.rv

class HomeActivity : AppCompatActivity() {
    private val mData = arrayOf("JiuGongGeActivity","LayerActivity", "FlowActivity","FlyActivity","LayoutStatesActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rv.adapter = object : BaseRecyclerAdapter<String>(mData.toList()) {
            override fun getItemLayoutId(viewType: Int): Int {
                return android.R.layout.simple_list_item_1
            }

            override fun convert(viewHolder: BaseVH, viewType: Int, data: String, position: Int) {
                viewHolder.setText(android.R.id.text1, data)
                viewHolder.itemView.setOnClickListener {
                    when (position) {
                        0 -> startActivity(Intent(this@HomeActivity, JiuGongGeActivity::class.java))
                        1 -> startActivity(Intent(this@HomeActivity, LayerActivity::class.java))
                        2 -> startActivity(Intent(this@HomeActivity, FlowActivity::class.java))
                        3 -> startActivity(Intent(this@HomeActivity, FlyActivity::class.java))
                        4 -> startActivity(Intent(this@HomeActivity, LayoutStatesActivity::class.java))
                    }
                }
            }

        }
    }
}