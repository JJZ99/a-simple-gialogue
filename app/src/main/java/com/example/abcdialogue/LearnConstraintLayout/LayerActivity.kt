package com.example.abcdialogue.LearnConstraintLayout

import android.animation.ValueAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.abcdialogue.R
import kotlinx.android.synthetic.main.activity_layer.button8
import kotlinx.android.synthetic.main.activity_layer.layer
import kotlin.math.abs
import kotlin.math.cos

class LayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layer)

        button8.setOnClickListener {

            val anim = ValueAnimator.ofFloat(0f, 360f)
            anim.addUpdateListener { animation ->
                val angle = animation.animatedValue as Float
                //设置旋转
                layer.rotation = angle
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    //设置layer的透明度，
                    layer.transitionAlpha = 1F - (180 - abs(angle - 180))/90

                }
                //设置x/y的缩放
                layer.scaleX = 1 + (180 - abs(angle - 180))/90
                layer.scaleY = 1 + (180 - abs(angle - 180))/90

                //cos的参数单位是弧度，toRadians是将角度换算为弧度
                var shift_x = 500 - 500 * cos(Math.toRadians(angle.toDouble())).toFloat()
                var shift_y = 500 - 500 * cos(Math.toRadians(angle.toDouble())).toFloat()
                //设置layer中view的移动
                layer.translationX = shift_x
                layer.translationY = shift_y
                Log.i("layer--x",shift_x.toString())
                Log.i("layer--y",shift_y.toString())

            }
            anim.duration = 4000
            anim.start()
        }
    }
}