package com.example.abcdialogue.LearnConstraintLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat.generateViewId
import androidx.transition.TransitionManager
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.btn_a
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.root
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.tv3

/**
 * 这个类主要是对约束布局ConstraintSet最基本的学习
 */
class ConstrainLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constrain_layout4placeholder)


        btn_a.findViewById<Button>(R.id.btn_a).setOnClickListener {
            "你点击了添加按钮".toastSuccess()
            //set a new textview
            val tvAdd = TextView(this)
            tvAdd.id = generateViewId()
            tvAdd.text = "三顿饭"
            tvAdd.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20F)
            tvAdd.visibility = View.VISIBLE
            tvAdd.setBackgroundResource(R.color.colorPrimary)
            //Todo ConstraintLayout.LayoutParams.leftMargin和ConstraintSet.Layout.leftMargin这两个什么区别,哪个优先级高
            //这里不能用ViewGroup.LayoutParam
//            tvAdd.layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
//                leftMargin = dip2px(20F)
//            }
           //


            var constrainSet = ConstraintSet().apply {
                root.addView(tvAdd)

                //Todo ConstrainSet.Layout是干啥的

                clone(root)
                constrainHeight(R.id.btn_a,600)
                constrainWidth(R.id.btn_a,300)
//                setAlpha()
//                setRotation()
//                setScaleX()




//                connect(R.id.tv3, ConstraintSet.END, tvAdd.id, ConstraintSet.START)
//                connect(tvAdd.id, ConstraintSet.TOP, R.id.tv3, ConstraintSet.TOP)
//                connect(tvAdd.id, ConstraintSet.BOTTOM, R.id.tv3, ConstraintSet.BOTTOM)
//                connect(tvAdd.id, ConstraintSet.TOP, R.id.tv3, ConstraintSet.TOP)
                //设置障碍的参考Id
                //setReferencedIds()

                //setHorizontalBias(tvAdd.id,0.5F)

                //这个方法会重置margin为0，其实也是用connect实现
                //addToHorizontalChainRTL(tvAdd.id, R.id.tv3, ConstraintSet.PARENT_ID)
                //setMargin(tvAdd.id,1, dip2px(20F))

                //addToVerticalChain(tvAdd.id,R.id.tv3,R.id.tv3)
                //设置链的样式
                //setHorizontalChainStyle(R.id.tv1,2)
            }

            root.setConstraintSet(constrainSet)


            //使用默认过渡为新场景设置动画的便捷方法，该新场景由调用此方法和下一个渲染帧之间给定场景根内的所有更改定义。
            //等效于调用beginDelayedTransition(ViewGroup, Transition)的transition参数值为null
            constrainSet.applyTo(root)
            TransitionManager.beginDelayedTransition(findViewById<ConstraintLayout>(R.id.root))


        }
    }
    private fun useLayoutParam(textView: TextView){
        val ivRightLayoutParams: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            WRAP_CONTENT, WRAP_CONTENT
        )
        ivRightLayoutParams.leftToRight = R.id.tv3
        ivRightLayoutParams.rightToRight = R.id.root
        ivRightLayoutParams.topToTop = R.id.tv3
        ivRightLayoutParams.bottomToBottom = R.id.tv3
        ivRightLayoutParams.leftMargin = 60

        textView.layoutParams = ivRightLayoutParams
        (tv3.layoutParams as ConstraintLayout.LayoutParams).apply {
            this.rightToLeft = textView.id
        }

        root.addView(textView)
        //findViewById<ConstraintLayout>(R.id.root).requestLayout()
    }


}