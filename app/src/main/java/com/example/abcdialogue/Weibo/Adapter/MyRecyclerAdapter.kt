package com.example.abcdialogue.Weibo.Adapter


import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder.Companion.LOADER_STATE_ING


class MyRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onItemClickListener : OnItemClickListener?= null
    var onLoadMoreListener : OnLoadMoreListener?= null

    var count = 16
    var lastLoadDataItemPosition = count-1
    var pageSize = 10
    var pageCount = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL)
            MyRecyclerHolder.create(parent, onItemClickListener, ++time)
        else{
            MyFooterViewHolder.create(parent,onLoadMoreListener)
        }
    }

    /**
     * 这里的bind和下面的注释部分不同，因为这里是父类，那意味着只要是他的子类hold都ok
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position!=(itemCount-1))
            (holder as MyRecyclerHolder).textView2.text = position.toString()
        else{
            if (currStatus == LoadStatus.LoadMoreIn){
                (holder as MyFooterViewHolder).update(LOADER_STATE_ING)
            }
        }
    }

    /**
     * 这个方法一般是用来绑定holder的，一般用来设置数据
     */
//    override fun onBindViewHolder(holder: MyRecyclerHolder, position: Int) {
//        if (position!=(itemCount-1))
//            holder.textView2.text = position.toString()
//
//    }

    override fun getItemCount(): Int {
        return count
    }

    /**
     * 获取Item的数据类型
     */
    override fun getItemViewType(position: Int): Int {
        return if (position != (itemCount - 1)) {
            TYPE_NORMAL
        } else {
            TYPE_LOAD_MORE
        }
    }

    /**
     * 添加数据
     */
     fun addItem(pos:Int){
        ++count
        //刷新适配器
        notifyItemInserted(pos)
        notifyItemRangeChanged(pos,1)

    }

    fun removeItem(pos:Int){
        notifyItemRemoved(pos)
        --count
        notifyItemRangeChanged(pos,1)

    }

    //每一项的点击事件
    public interface OnItemClickListener{
        fun onItemClick(view:View,pos:Int)
    }

    //加载更多的监听接口
    public interface OnLoadMoreListener{
        fun onLoadMore(view: MyFooterViewHolder)
    }

    companion object {
        var time = 0
        //最后一项
        const val FOOTER = 1
        //中间的
        const val MIDDLE = 0
        //头部
        const val HEADER = -1

        const val TYPE_NORMAL = 0
        const val TYPE_LOAD_MORE = 1
        var currStatus = LoadStatus.LoadMoreIn


    }



}
