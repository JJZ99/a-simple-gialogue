package com.example.abcdialogue.Weibo.Adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.abcdialogue.Weibo.Bean.WBStatusBean

//diffCallBack的升级版，AsyncListDiffer
// AsyncListDiffer底层封装的是DiffCallBack。进行diff操作前或切到其他线程，最后在更新UI的时候会切回主线程，更新ui还是调用了dispatchUpdatesTo。
class MyItemCallBack(private var oldList: MutableList<WBStatusBean>,
                     private var newList: MutableList<WBStatusBean>,) : DiffUtil.ItemCallback<WBStatusBean>(){


    override fun areItemsTheSame(oldItem: WBStatusBean, newItem: WBStatusBean): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: WBStatusBean, newItem: WBStatusBean): Boolean {
        return oldItem == newItem
    }

    //和DiffCallBack同理
    override fun getChangePayload(oldItem: WBStatusBean, newItem: WBStatusBean): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}