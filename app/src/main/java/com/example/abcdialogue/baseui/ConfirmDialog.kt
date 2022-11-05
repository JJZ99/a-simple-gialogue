package com.example.abcdialogue.baseui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.abcdialogue.R

class ConfirmDialog(context: Context):Dialog(context) {
    var leftClickAction: (isChecked: Boolean, context: Context) -> Unit = { _, _ -> }//左边按钮的点击
    var rightClickAction: (isChecked: Boolean,context: Context) -> Unit = {  _ ,_ -> }//右边按钮的点击

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_confirm_dialog)

        window?.let{
            it.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            it.attributes.gravity = Gravity.CENTER
        }
        setCancelable(true)

        findViewById<TextView>(R.id.tv_left).also {
            it.text = "取消"
            it.setOnClickListener {
                leftClickAction.invoke(false, context)
            }
        }
        findViewById<TextView>(R.id.tv_right).also{
            it.text = "确认"
            it.setOnClickListener {
                rightClickAction.invoke(true, context)
            }
        }
    }

}
