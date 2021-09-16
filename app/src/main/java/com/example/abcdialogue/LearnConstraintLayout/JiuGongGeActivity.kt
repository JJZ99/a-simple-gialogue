package com.example.abcdialogue.LearnConstraintLayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.transition.TransitionManager
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.Util.DisplayUtil.dip2px
import com.example.abcdialogue.Weibo.Util.DisplayUtil.getWindowsWidth
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastError
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.root
import kotlinx.android.synthetic.main.activity_jiu_gong_ge.btn_add
import kotlinx.android.synthetic.main.activity_jiu_gong_ge.btn_del
import java.util.Stack

/**
 * 使用ConstraintSet来添加删除控件
 * 1、每行最多三个最少一个，最多三行
 * 2、每行或每列应该是个链
 * 3、要加上动画哦
 * */
class JiuGongGeActivity : AppCompatActivity() {


    //每个view的宽
    val width by lazy {
        ((getWindowsWidth() - marginTopOut*3 - marginLeftIn * 2*3) / 3).toInt()
    }

    //用来存放ID
    val queue = ArrayDeque<Int>()
    //多少行
    var rowCount = 0
    //多少个
    var remainder = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jiu_gong_ge)


        //添加
        btn_add.setOnClickListener {
            it.isClickable = false

            add()


            it.isClickable = true
        }
        //删除
        btn_del.setOnClickListener {
            it.isClickable = false






            it.isClickable = true
        }


    }
    private fun add() {
        val textView = getView()
        if (textView == null) {
            "满了、别加了".toastInfo()
            return
        }
        val id = textView.id
        root.addView(textView)
        //创建
        val constraintSet = ConstraintSet().apply {
            //获取约束集
            clone(root)
            //Todo 这里需要怎么写啊，不会啊
            //setReferencedIds(R.id.barrier2,queue[0])

            //设置宽高
            constrainWidth(id, width)
            constrainHeight(id, ConstraintSet.MATCH_CONSTRAINT)
            setDimensionRatio(id,"1")

            if (remainder == 1) {
                //设置水平链的样式，2===》packed
                setHorizontalChainStyle(id, 2)

                //设置水平方向的约束
                addToHorizontalChainRTL(id, ConstraintSet.PARENT_ID, ConstraintSet.PARENT_ID)
                //设置左边距，1表示左
                setMargin(id, 6, dip2px(marginLeftOut))
                //设置偏移，靠左放置
                setHorizontalBias(id, 0F)
                //设置垂直方向的约束，只约束top就够用了
                connect(id,
                    ConstraintSet.TOP,
                    getTopViewId(),
                    if (rowCount != 1) ConstraintSet.BOTTOM else ConstraintSet.TOP,
                    if (rowCount != 1) dip2px(marginTopIn) else dip2px(marginTopOut))
            }else{
                //设置水平方向的约束
                addToHorizontalChainRTL(id,getPreviousId(),ConstraintSet.PARENT_ID)
                //设置左边距，1表示左
                setMargin(id, 6, dip2px(marginLeftIn))
                //设置竖直方向的约束
                connect(id,
                    ConstraintSet.TOP,
                    getPreviousId(),
                    ConstraintSet.TOP)
            }


        }

        constraintSet.applyTo(root)
        TransitionManager.beginDelayedTransition(root)
        //把id存起来
        queue.addLast(id)
    }

    /**
     * 返回队列的最后一个id
     */
    private fun getPreviousId(): Int {
        return queue.last()
    }

    private fun getTopViewId(): Int {
        return when (rowCount) {
            1 -> ConstraintSet.PARENT_ID
            2 -> queue[0]
            3 -> queue[3]
            else -> ConstraintSet.PARENT_ID
        }
    }

    private fun delete() {
        if (queue.size == 0) {
            "过分了啊".toastError()
            return
        }



        TransitionManager.beginDelayedTransition(root)
        queue.removeLast()

    }
    private fun getView(): TextView ? {
        if (queue.size == 9) {
            return null
        }
        //Todo 这里直接增1不合适，应该在成功添加后再加一，或者添加失败后还原
        if (remainder % 3 == 0) {
            rowCount++
            remainder = 1
        } else {
            remainder++
        }
        return TextView(this).apply {
            id = ViewCompat.generateViewId()
            text = "${remainder+(rowCount-1)*3}"

            setTextSize(TypedValue.COMPLEX_UNIT_DIP,30F)
            gravity = 17
            visibility = View.VISIBLE
            setBackgroundResource(R.color.colorPrimary)

        }

    }

    companion object {
        const val marginTopOut = 15F
        const val marginLeftOut = 10F

        const val marginTopIn = 7F
        const val marginLeftIn = 6F
    }
}