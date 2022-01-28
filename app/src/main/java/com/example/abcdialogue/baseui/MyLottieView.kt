package com.example.abcdialogue.baseui

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import com.airbnb.lottie.LottieAnimationView

class MyLottieView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
) : LottieAnimationView(context, attrs) {
    val LOG = "MyLottieView"
    init {
        Log.i(LOG,"init")
        setImageFolder("images")
    }
    override fun onDetachedFromWindow() {
        Log.i(LOG,"onDetachedFromWindow")
        super.onDetachedFromWindow()
        cancelAnimation()
    }

    fun initParams() {}

    private fun setImageFolder(image: String) {
        Log.i(LOG,"setImageFolder")
        imageAssetsFolder = image
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        Log.i(LOG, "setVisibility")
        //cancelAnimation()
    }

}