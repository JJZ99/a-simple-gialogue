package com.example.abcdialogue.Weibo.Adapter

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.R

class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerHolder>() {
    var onItemClickListener : AdapterView.OnItemClickListener?= null
    var count = 16

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerHolder {
        return MyRecyclerHolder.create(parent,onItemClickListener,++Companion.time)
    }

    override fun onBindViewHolder(holder: MyRecyclerHolder, position: Int) {
        //holder.textView.text = position.toString()
    }

    override fun getItemCount(): Int {
        return count
    }


    /**
     * 添加数据
     */
     fun addItem(pos:Int){
        ++count
        //刷新适配器
        notifyItemInserted(pos)
    }

    fun removeItem(pos:Int){
        notifyItemRemoved(pos)
        --count
    }

    public interface OnItemClickListener{
        fun onItemClick(view:View,pos:Int)
    }

    companion object {
        var time = 0
    }

}
