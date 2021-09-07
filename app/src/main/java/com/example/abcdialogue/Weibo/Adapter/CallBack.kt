package com.example.abcdialogue.Weibo.Adapter

import android.view.View

//每一项的点击事件
public interface OnItemClickListener{
    fun onItemClick(view: View, pos:Int)
}

//加载更多的监听接口
public interface OnLoadMoreListener{
    fun onLoadMore(view: MyFooterViewHolder)
}

//删除图片的监听接口
public interface OnDeleteImageListener{
    fun onDeleteImageListener(position: Int, imageUrl: String?)

}
