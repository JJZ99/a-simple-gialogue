package com.example.abcdialogue.Adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.R

class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerHolder>() {
    var count = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerHolder {
        return MyRecyclerHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MyRecyclerHolder, position: Int) {

        if (position%2!=0){
          //  holder.image.setImageResource(R.drawable.image3)

        }else{
           // holder.image.setImageResource(R.drawable.image4)

        }
        ++count
    }


    override fun getItemCount(): Int {
        return 16
    }
}