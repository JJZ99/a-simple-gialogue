package com.example.abcdialogue.LearnConstraintLayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.helper.widget.Layer
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos


class FlyHelper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Layer(context, attrs, defStyleAttr) {

    override fun updatePostLayout(container: ConstraintLayout) {
        super.updatePostLayout(container)
        //获取中心点
        val centerPoint = PointF(((left + right) / 2).toFloat(), ((top + bottom) / 2).toFloat())
        //5秒的动画
        val animator = ValueAnimator.ofFloat(0f, 1f).setDuration(4000)
        animator.addUpdateListener { animation ->
            val animatedFraction = animation.animatedFraction
            getViews(container).forEach {view ->
                val viewCenterX = (view.left + view.right) / 2
                val viewCenterY = (view.top + view.bottom) / 2
                val startTranslationX = if (viewCenterX < centerPoint.x) -1000f else 1000f
                val startTranslationY = if (viewCenterY < centerPoint.y) -1000f else 1000f
                with(view){
                    //设置角度
                    rotation = animatedFraction * 360 * 5
                    //设置透明度，
                    alpha = abs(cos( 8 * PI * animatedFraction)).toFloat()
                    print("alpha"+alpha.toString())
                    //设置x/y的缩放
                    scaleX = 2-animatedFraction
                    scaleY = 2-animatedFraction
                    //设置偏移
                    translationX = (1 - animatedFraction) * startTranslationX
                    translationY = (1 - animatedFraction) * startTranslationY
                }
            }
        }
        animator.start()
    }
}