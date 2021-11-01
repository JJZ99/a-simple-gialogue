package com.example.abcdialogue.Weibo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.state.LoadStatus

class MyFooterViewHolder(private val itemView: View,private val onLoadMoreListener: MyRecyclerAdapter.OnLoadMoreListener?): RecyclerView.ViewHolder(itemView) {
    val loading = itemView.findViewById<LinearLayout>(R.id.load_more_ing)
    val loadFail = itemView.findViewById<TextView>(R.id.load_more_fail)
    val loadEnd = itemView.findViewById<TextView>(R.id.no_more)


    /**
     * 根据状态码来更新底部控件状态
     */
    fun update(state: LoadStatus) {
        when (state) {
            LoadStatus.LoadMoreIn -> {
                loading.visibility = View.VISIBLE;loadFail.visibility =
                    View.GONE;loadEnd.visibility = View.GONE
                //在这里面去加载数据
                startLoaderMore()
            }
            LoadStatus.LoadMoreSuccess -> {
                loading.visibility = View.GONE;loadFail.visibility = View.GONE;loadEnd.visibility =
                    View.GONE
            }
            LoadStatus.LoadMoreError -> {
                loading.visibility = View.GONE;loadFail.visibility =
                    View.VISIBLE;loadEnd.visibility = View.GONE
            }
            LoadStatus.LoadMoreEnd -> {
                loading.visibility = View.GONE;loadFail.visibility = View.GONE;loadEnd.visibility =
                    View.VISIBLE
            }
        }
    }

    private fun startLoaderMore() {
        onLoadMoreListener?.onLoadMore(this)
    }

    companion object {
        fun create( parent: ViewGroup, onLoadMoreListener: MyRecyclerAdapter.OnLoadMoreListener?): MyFooterViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_more_item, parent, false)
            val viewHolder = MyFooterViewHolder(itemView,onLoadMoreListener)
            itemView.setOnClickListener{
               // "你点到了foot".toastInfo()
            }
            viewHolder.loadFail.setOnClickListener {
                //加载失败后重新加载
                onLoadMoreListener?.let {
                    it.onLoadMore(viewHolder)
                }
            }

            return viewHolder
        }

        const val LOADER_STATE_ING = 0   // 列表往下加载更多中
        const val LOADER_STATE_SUCCESS = 1    // 列表往下加载成功
        const val LOADER_STATE_FAIL = 2    // 列表往下加载失败
        const val LOADER_STATE_END = 3   // 列表往下已全部加载完毕
    }
}