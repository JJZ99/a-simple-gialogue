package com.example.abcdialogue.Weibo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.R
import com.facebook.drawee.view.SimpleDraweeView

class MyRecyclerHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {
    var textView :TextView = itemView.findViewById(R.id.header_name)
    var textView2 :TextView = itemView.findViewById(R.id.header_time)
    var content :TextView = itemView.findViewById(R.id.tv_content)
    var headerImage :SimpleDraweeView = itemView.findViewById(R.id.header_image)
    var imageContainer :LinearLayout = itemView.findViewById(R.id.image_container)




    companion object {
        fun create(parent: ViewGroup,onItemClickListener: MyRecyclerAdapter.OnItemClickListener?): MyRecyclerHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.new_item, parent, false)
            val viewHolder = MyRecyclerHolder(itemView)
            itemView.setOnClickListener{
                onItemClickListener?.let {
                    it.onItemClick(parent,viewHolder.layoutPosition)
                }
            }
            return viewHolder
        }
    }
}
