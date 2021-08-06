package com.example.abcdialogue.Weibo.Adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerHolder {
        return MyRecyclerHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MyRecyclerHolder, position: Int) {


    }


    override fun getItemCount(): Int {
        return 16
    }
}