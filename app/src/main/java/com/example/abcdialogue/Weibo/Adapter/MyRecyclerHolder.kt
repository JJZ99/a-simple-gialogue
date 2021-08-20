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
    var source: TextView = itemView.findViewById(R.id.source)
    var sourceTextView: TextView = itemView.findViewById(R.id.source_tv)

    var content :TextView = itemView.findViewById(R.id.tv_content)
    var headerImage :SimpleDraweeView = itemView.findViewById(R.id.header_image)
    var imageContainer :LinearLayout = itemView.findViewById(R.id.image_container)
    var forwardTextView: TextView = itemView.findViewById(R.id.forward_tv)
    var commentTextView: TextView = itemView.findViewById(R.id.comment_tv)
    var likeTextView: TextView = itemView.findViewById(R.id.like_tv)





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
