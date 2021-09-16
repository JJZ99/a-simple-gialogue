package com.example.abcdialogue

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams.PARENT_ID
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat.generateViewId
import androidx.core.view.marginLeft
import androidx.transition.TransitionManager
import com.example.abcdialogue.Weibo.Util.DisplayUtil.dip2px
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.btn_a
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.root
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.tv3
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.tv2

class ConstrainLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constrain_layout4placeholder)

        btn_a.findViewById<Button>(R.id.btn_a).setOnClickListener {
            //set a new textview
            val tvAdd = TextView(this)
            tvAdd.id = generateViewId()
            tvAdd.text = "三顿饭"
            tvAdd.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20F)
            tvAdd.visibility = View.VISIBLE
            tvAdd.setBackgroundResource(R.color.colorPrimary)
            //这里不能用ViewGroup.LayoutParam
            tvAdd.layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
                //leftMargin = dip2px(20F)
            }
           //
            root.addView(tvAdd)

            var constrainSet = ConstraintSet().apply {
                //Todo ConstrainSet.Layout是干啥的

                //this.setMargin(R.id.tv1,1,200)
                clone(findViewById<ConstraintLayout>(R.id.root))
                addToHorizontalChainRTL(tvAdd.id, R.id.tv3, ConstraintSet.PARENT_ID)
                setHorizontalChainStyle(R.id.tv1,1)
            }

//            constrainSet.connect(R.id.tv3,ConstraintSet.END,tvAdd.id,ConstraintSet.START)
//            constrainSet.connect(tvAdd.id,ConstraintSet.TOP,R.id.tv3,ConstraintSet.TOP)
//            constrainSet.connect(tvAdd.id,ConstraintSet.BOTTOM,R.id.tv3,ConstraintSet.BOTTOM)
//            constrainSet.connect(tvAdd.id,ConstraintSet.START,R.id.tv3,ConstraintSet.END)



            //findViewById<ConstraintLayout>(R.id.root).setConstraintSet(constrainSet)

            constrainSet.applyTo(findViewById(R.id.root))

            //(tv2.layoutParams as ViewGroup.MarginLayoutParams).setMargins(50,50,50,50)




//            //
//
//            constrainSet.applyTo(findViewById(R.id.root))
            //findViewById<ConstraintLayout>(R.id.root).requestLayout()

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