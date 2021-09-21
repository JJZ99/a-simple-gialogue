package com.example.abcdialogue.LearnConstraintLayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

class CircularHelper  @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintHelper(context, attrs, defStyleAttr) {

    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)
        val views = getViews(container)
        views.forEach {
            val anim = ViewAnimationUtils.createCircularReveal(it, 0, 0, 0f, it.height.toFloat())
            anim.duration = 4000
            anim.start()
        }
    }
}