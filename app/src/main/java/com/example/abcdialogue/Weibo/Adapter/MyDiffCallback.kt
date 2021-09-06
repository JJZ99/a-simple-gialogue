package com.example.abcdialogue.Weibo.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.abcdialogue.Weibo.Bean.WBStatusBean

class MyDiffCallback(
    private var oldList: MutableList<WBStatusBean>,
    private var newList: MutableList<WBStatusBean>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    //这个方法要配合 recycler的bindHolder的另一个三参重载方法，我们可以返回一个list，里面放那些有变动的数据
    //然后我们在bindHolder中去取list根据他类更改对应Item的子view，达到局部刷新的效果
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    //判断是否是同一个Item
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    //判断这些Item内容是否相同
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}