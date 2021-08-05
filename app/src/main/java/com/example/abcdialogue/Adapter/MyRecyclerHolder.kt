package com.example.abcdialogue.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.R

class MyRecyclerHolder(private val itemView: View): RecyclerView.ViewHolder(itemView) {


    companion object {
        fun create(parent: ViewGroup): MyRecyclerHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.new_item, parent, false)

            return MyRecyclerHolder(itemView)
        }
    }
}