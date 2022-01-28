package com.example.abcdialogue.Test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.lottie.LottieDrawable
import com.example.abcdialogue.R
import kotlinx.android.synthetic.main.activity_lottie.btn_empty
import kotlinx.android.synthetic.main.activity_lottie.btn_error
import kotlinx.android.synthetic.main.activity_lottie.btn_loading
import kotlinx.android.synthetic.main.activity_lottie.lottie

class LottieActivity : AppCompatActivity() {
    val LOG = "LottieActivity"

    var lottieFileName: String = "shopping_anim_load_more_lottie.json"
    @LottieDrawable.RepeatMode var repeatMode: Int = LottieDrawable.RESTART
    var repeatCount: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)
        lottie.let {
            it.visibility = View.VISIBLE
            it.setAnimation(lottieFileName)
            it.setRepeatMode(repeatMode);//设置播放模式
            it.setRepeatCount(repeatCount);//设置重复次数
            //it.playAnimation()
        }

        btn_loading.setOnClickListener {
            Log.i(LOG, lottie.isAnimating.toString())
            Log.i(LOG, lottie.visibility.toString())
            lottie.playAnimation()
            Log.i(LOG,lottie.visibility.toString())
        }
        btn_empty.setOnClickListener {
            Log.i(LOG,lottie.isAnimating.toString())
            Log.i(LOG,lottie.visibility.toString())
            lottie.visibility = View.GONE
            Log.i(LOG,lottie.visibility.toString())
        }
        btn_error.setOnClickListener {
            Log.i(LOG,lottie.isAnimating.toString())

            Log.i(LOG,lottie.visibility.toString())
            lottie.visibility = View.INVISIBLE
            Log.i(LOG,lottie.visibility.toString())
        }

    }
}