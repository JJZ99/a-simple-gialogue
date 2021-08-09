package com.example.abcdialogue.Weibo.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.MyApplication.Companion.context
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.DisplayUtil
import com.example.abcdialogue.Util.Util.toastShort
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder.Companion.LOADER_STATE_END
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder.Companion.LOADER_STATE_ING
import com.example.abcdialogue.Weibo.Util.FrescoUtil
import com.example.abcdialogue.Weibo.VM.CountryViewModel
import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.image_linear_hor.view.new_image_hor
import kotlinx.coroutines.internal.artificialFrame


class MyRecyclerAdapter(private var fragment:Fragment,var viewModel: CountryViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onItemClickListener : MyRecyclerAdapter.OnItemClickListener?= null
    var onLoadMoreListener : OnLoadMoreListener?= null

    //这里多一项是因为footer
    private var total = 1

    private var winWidth = context.resources.displayMetrics.widthPixels

    private var itemMarginEnd = DisplayUtil.dp2px(3)
    private var itemWidth = (winWidth - itemMarginEnd * 3 - 36) / 3

    /**
     * 是否能加载更多
     * 在初始化和增加删除某一项后会判断设置值
     */
    private var hasMore = false

    var pageSize = 30
    var currNumber = 1
    init {
        viewModel.countryList.observe(fragment.viewLifecycleOwner,{
            total = it.size+1
            if (currNumber<total) {
                hasMore = false
                currStatus = LoadStatus.LoadMoreIn
            }
            notifyDataSetChanged()
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
                FrescoUtil.loadImage(headerImage,"")
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
                                "width${childView.width}\nheight${childView.height}\n$winWidth".toastShort()
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
                                "width${childView.width}\nheight${childView.height}\n$winWidth".toastShort()
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
        if (currNumber==total){
            currStatus = LoadStatus.LoadMoreEnd
            hasMore = false
            return
        }
        ++currNumber
        //刷新适配器
        notifyItemInserted(pos)
        notifyItemRangeChanged(pos,1)
    }

    fun removeItem(pos:Int){
        //剩下一个加载更多的时候就是1
        if (currNumber==1){
            if (total==currNumber){
                hasMore = false
                currStatus = LoadStatus.LoadMoreEnd
            }else{
                hasMore = true
                currStatus = LoadStatus.LoadMoreIn
            }
            return
        }

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
        var currStatus = LoadStatus.LoadMoreEnd
    }
}

