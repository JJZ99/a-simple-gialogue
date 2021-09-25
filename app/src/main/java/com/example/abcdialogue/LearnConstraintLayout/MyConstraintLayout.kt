package com.example.abcdialogue.LearnConstraintLayout

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastSuccess

class MyConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    var count = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        "${++count}".toastSuccess()
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    }

}