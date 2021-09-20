package com.example.abcdialogue.LearnConstraintLayout

import android.animation.ValueAnimator
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
import kotlinx.android.synthetic.clearFindViewByIdCache
import kotlinx.android.synthetic.main.activity_constrain_layout4placeholder.root
import kotlinx.android.synthetic.main.activity_jiu_gong_ge.barrier2
import kotlinx.android.synthetic.main.activity_jiu_gong_ge.btn_add
import kotlinx.android.synthetic.main.activity_jiu_gong_ge.btn_del
import java.util.Stack

/**
 * 使用ConstraintSet来添加删除控件
 * 1、每行最多三个最少一个，最多三行
 * 2、每行或每列应该是个链
 * */
class JiuGongGeActivity : AppCompatActivity() {


    //每个view的宽
    val width by lazy {
        ((getWindowsWidth() - marginTopOut * 3 - marginLeftIn * 2 * 3) / 3).toInt()
    }

    //用来存放ID
    val queue = ArrayDeque<Int>()
    //多少行
    var rowCount = 0
    //多少个
    var remainder = 0
    //链的样式
    var chainStyle = 0

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
            delete()
            it.isClickable = true
        }


    }


    /**
     * 返回队列倒数第二个id
     */
    private fun getPreviousId(): Int {
        var index = queue.size - 2
        if (index == -1) {
            return queue.last()
        }
        return queue[index]
    }

    private fun getTopViewId(): Int {
        return when (rowCount) {
            1 -> ConstraintSet.PARENT_ID
            2 -> queue[0]
            3 -> queue[3]
            else -> ConstraintSet.PARENT_ID
        }
    }
    private fun add() {
        val textView = createView() ?: return
        root.addView(textView)
        val id = textView.id
        //创建
        ConstraintSet().apply {
            isForceId = false
            //获取约束集
            clone(root)

            setAlpha(id, remainder * 0.3F)

            //Todo 如果可变参数你传的是个数组，那么应该在前面加*
            setReferencedIds(R.id.barrier2, *queue.toIntArray())
            //设置宽高和比例
            constrainWidth(id, width)
            constrainHeight(id, ConstraintSet.MATCH_CONSTRAINT)
            setDimensionRatio(id,"0.618")

            if (remainder == 1) {
                //设置水平链的样式，2===》packed
                setHorizontalChainStyle(id, chainStyle++)
                //设置水平方向的约束
                addToHorizontalChainRTL(id, ConstraintSet.PARENT_ID, ConstraintSet.PARENT_ID)
                //设置左边距，1表示左
                setMargin(id, ConstraintSet.START, dip2px(marginLeftOut))
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
            TransitionManager.beginDelayedTransition(root)
            applyTo(root)
        }
    }
    private fun delete() {
        val id = getWillRemoveId() ?: return
        //移除id
        queue.removeLast()

        ConstraintSet().apply {
            isForceId = false

            //获取约束集
            clone(root)
            //其实这里不用清除，你可以直接把view从root中移除，然后再设置其他的约束
            clear(id)
            setReferencedIds(R.id.barrier2, *queue.toIntArray())
            //等于3说明要移除的是头部
            if (remainder == 3) {
                chainStyle--
            }else{
                //设置前一个水平方向的约束
                connect(getPreviousId(),
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END)
            }
            TransitionManager.beginDelayedTransition(root)
            applyTo(root)
        }
        //把视图移除
        root.removeView(root.getViewById(id))


    }

    private fun getWillRemoveId(): Int ? {
        if (queue.size == 0) {
            "过分了啊".toastError()
            return null
        }
        --remainder
        if (remainder == 0) {
            remainder = 3
            rowCount--
        }
        return queue.last()
    }

    private fun createView(): TextView ? {
        if (queue.size == 9) {
            "满了、别加了".toastInfo()
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
            //把id存起来
            queue.addLast(id)
        }
    }

    companion object {
        const val marginTopOut = 15F
        const val marginLeftOut = 10F

        const val marginTopIn = 7F
        const val marginLeftIn = 6F
    }
}