package com.example.abcdialogue.Weibo.Adapter


import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder.Companion.LOADER_STATE_END
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder.Companion.LOADER_STATE_ING
import com.example.abcdialogue.Weibo.Util.FrescoUtil
import com.example.abcdialogue.Weibo.VM.CountryViewModel


class MyRecyclerAdapter(private var fragment:Fragment,var viewModel: CountryViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onItemClickListener : OnItemClickListener?= null
    var onLoadMoreListener : OnLoadMoreListener?= null

    var listCountry = viewModel.countryList.value


    //这里多一项是因为footer
    private var total = (listCountry?.size?:0)+1


    var pageSize = 30
    var pageCount = 0
    var remainder = 1
    init {
        viewModel.countryList.observe(fragment.viewLifecycleOwner,{
            total = it.size+1
            notifyDataSetChanged()
        })
        if (total==1){
            remainder = 1
        }

        if (total in 2..29) {
            pageCount = 0
        }
        if (total>=30){
            pageCount = 1
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL)
            MyRecyclerHolder.create(parent, onItemClickListener)
        else{
            MyFooterViewHolder.create(parent,onLoadMoreListener)
        }
    }

    /**
     * 这里的bind和下面的注释部分不同，因为这里是父类，那意味着只要是他的子类hold都ok
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position!=(itemCount-1)){
            (holder as MyRecyclerHolder).apply{
                textView.text = position.toString()
                textView2.text = position.toString()
                viewModel.countryList.value?.let {
                    textView.text = it[position].statusMsg
                    textView2.text = it[position].statusCode
                }

                FrescoUtil.loadImage(imageView,"")
                FrescoUtil.loadImage(headerImage,"")
            }
        } else{
            if (currStatus == LoadStatus.LoadMoreIn){
                (holder as MyFooterViewHolder).update(LOADER_STATE_ING)
            }
            if (currStatus == LoadStatus.LoadMoreEnd){
                (holder as MyFooterViewHolder).update(LOADER_STATE_END)
            }
            (holder as MyFooterViewHolder).update(LOADER_STATE_END)
        }
    }


    override fun getItemCount(): Int {
        return (pageCount*pageSize)+remainder
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
        if ((pageCount*pageSize)+remainder==total-1){
            currStatus = LoadStatus.LoadMoreEnd
            return
        }

        ++remainder
        //刷新适配器
        notifyItemInserted(pos)
        notifyItemRangeChanged(pos,1)
        if (remainder==30){
            remainder=0
            pageCount++
        }
    }

    fun removeItem(pos:Int){
        if ((pageCount*pageSize)+remainder==0)
            return
        notifyItemRemoved(pos)
        --remainder
        notifyItemRangeChanged(pos,1)
        if (remainder==-1){
            remainder=29
            pageCount--
        }
        if ((pageCount * pageSize) + remainder <= total - 30) {
            currStatus = LoadStatus.LoadMoreIn


        }
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
        const val TYPE_NORMAL = 0
        const val TYPE_LOAD_MORE = 1
        var currStatus = LoadStatus.LoadMoreIn
    }
}

