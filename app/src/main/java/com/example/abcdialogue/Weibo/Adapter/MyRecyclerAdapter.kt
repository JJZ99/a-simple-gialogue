package com.example.abcdialogue.Weibo.Adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.MyApplication.Companion.context
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.DisplayUtil
import com.example.abcdialogue.Util.Util.toastInfo
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder.Companion.LOADER_STATE_END
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder.Companion.LOADER_STATE_ING
import com.example.abcdialogue.Weibo.Bean.WBStatusBean
import com.example.abcdialogue.Weibo.Model.WBViewModel
import com.example.abcdialogue.Weibo.Util.FrescoUtil

import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.image_linear_hor.view.new_image_hor


class MyRecyclerAdapter(private var fragment:Fragment,var viewModel: WBViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onItemClickListener : MyRecyclerAdapter.OnItemClickListener?= null
    var onLoadMoreListener : OnLoadMoreListener?= null

    //这里多一项是因为footer
    private var total = 1
    //手机的宽度
    private var winWidth = context.resources.displayMetrics.widthPixels
    //每一项的右边距
    private var itemMarginEnd = DisplayUtil.dp2px(3)
    //每一项的宽度
    private var itemWidth = (winWidth - itemMarginEnd * 3 - 36) / 3

    var currNumber = 1
    init {
        //这里多写一个是担心，如果在走到这里之前还没有完成第一次请求数据完成了那么
        viewModel.statusList.value?.let {
            total = it.size+1
            var addCounts = (page - 1) * 15-it.size
            //如果新增的等于15个 假设还有更多，否则就没有更多了
            hasMore = addCounts == 15
            Log.i("init adapter observe",viewModel.statusList.value.toString())
        }
        viewModel.statusList.observe(fragment.viewLifecycleOwner,{
            total = it.size+1
            var addCounts = (page - 1) * 15-it.size
            hasMore = addCounts == 15
            Log.i("init adapter observe",viewModel.statusList.value.toString())
            //notifyDataSetChanged()
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_NORMAL)
            MyRecyclerHolder.create(parent, onItemClickListener)
        else{
            MyFooterViewHolder.create(parent,onLoadMoreListener)
        }
    }

    /**
     * 根据position来判断是不是footer
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position!=(itemCount-1)){
            (holder as MyRecyclerHolder).apply{
                textView.text = position.toString()
                textView2.text = position.toString()
                viewModel.statusList.value?.let {
                    textView.text = it[position].name
                    textView2.text = it[position].createdAt
                    content.text = it[position].text
                    FrescoUtil.loadImageAddCircle(headerImage,it[position].avatarLarge)
                }

                //生成一个随机数[0,6]
                val randoms = (0..6).random()
                if (randoms==0){
                    holder.imageContainer.visibility = View.GONE
                }else{
                    holder.imageContainer.visibility = View.VISIBLE
                    holder.imageContainer.removeAllViews()
                    var count = randoms/3
                    var reminder = randoms%3
                    for (i in  0 until count){
                        val line = LayoutInflater.from(holder.imageContainer.context)
                            .inflate(R.layout.image_linear_hor, holder.imageContainer, false).apply {
                                this.layoutParams.height = itemWidth
                            }
                        for (j in 0 until 3){
                            val childView = SimpleDraweeView(line.context)
                            childView.setOnClickListener{
                                "width${childView.width}\nheight${childView.height}\n$winWidth".toastInfo()
                            }

                            FrescoUtil.loadImageAddSize(childView)
                            line.new_image_hor.addView(childView, LinearLayout.LayoutParams(itemWidth,
                                LinearLayout.LayoutParams.MATCH_PARENT).apply {
                                rightMargin = itemMarginEnd
                            })
                        }
                        holder.imageContainer.addView(line)
                    }
                    val footLine = LayoutInflater.from(holder.imageContainer.context)
                        .inflate(R.layout.image_linear_hor, holder.imageContainer, false).apply {
                            this.layoutParams.height = itemWidth
                        }
                    if (reminder!=0){
                        for (i in  0 until reminder){
                            val childView = SimpleDraweeView(footLine.context)
                            childView.setOnClickListener{
                                "width${childView.width}\nheight${childView.height}\n$winWidth".toastInfo()
                            }

                            FrescoUtil.loadImageAddSize(childView)
                            footLine.new_image_hor.addView(childView, LinearLayout.LayoutParams(itemWidth,
                                LinearLayout.LayoutParams.MATCH_PARENT).apply {
                                rightMargin = itemMarginEnd
                            })
                        }
                        holder.imageContainer.addView(footLine)
                    }
                }
            }
        } else{
            if (hasMore){
                (holder as MyFooterViewHolder).update(LOADER_STATE_ING)
            }else{
                (holder as MyFooterViewHolder).update(LOADER_STATE_END)
            }
        }
    }


    override fun getItemCount(): Int {
        return total
    }

    /**
     * 获取Item的数据类型
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == (itemCount - 1)) {
            TYPE_LOAD_MORE

        } else {
            TYPE_NORMAL
        }
    }

    /**
     * 添加数据
     */
     fun addItem(pos:Int,item:WBStatusBean){
        ++currNumber
        //刷新适配器
        notifyItemInserted(pos)
        notifyItemRangeChanged(pos,1)
    }

    fun removeItem(pos:Int){

        notifyItemRemoved(pos)
        --currNumber
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
        const val TYPE_NORMAL = 0
        const val TYPE_LOAD_MORE = 1
        //当前的状态，默认为加载成功
        var currStatus = LoadStatus.LoadMoreEnd
        //页码，也可以理解为加载了多少次
        const val PAGESIZE = 15
        //过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
        const val FEATURE = 2

        //初始页码为1
        var page = 1

        /**
         * 是否能加载更多
         * 这个值只在请求后判断是否有数据来设置，增加删除方法专注于内容的删改
         */
        var hasMore = false
    }
}

