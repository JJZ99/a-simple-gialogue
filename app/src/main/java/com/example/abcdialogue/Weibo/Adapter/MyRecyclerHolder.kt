package com.example.abcdialogue.Weibo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.Util.toastShort

class MyRecyclerHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
    var textView :TextView = itemView.findViewById(R.id.header_name)


    companion object {
        fun create(parent: ViewGroup,onItemClickListener: MyRecyclerAdapter.OnItemClickListener?,time:Int): MyRecyclerHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.new_item, parent, false)
            val viewHolder = MyRecyclerHolder(itemView)
            itemView.setOnClickListener{
                onItemClickListener?.let {
                    it.onItemClick(parent,viewHolder.layoutPosition)
                }
            }
            viewHolder.textView.text = time.toString()
            return viewHolder
        }
    }
}
