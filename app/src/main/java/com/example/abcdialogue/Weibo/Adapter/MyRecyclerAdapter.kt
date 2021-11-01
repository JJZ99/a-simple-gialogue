package com.example.abcdialogue.Weibo.Adapter


import android.content.Intent
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.MyApplication.Companion.context
import com.example.abcdialogue.Weibo.Util.DisplayUtil
import com.example.abcdialogue.Weibo.Bean.WBStatusBean
import com.example.abcdialogue.Weibo.Model.WBViewModel
import com.example.abcdialogue.Weibo.Util.FrescoUtil
import com.example.abcdialogue.Weibo.Util.ParseUtil.getFormatText
import com.example.abcdialogue.Weibo.Util.ParseUtil.getSource
import com.example.abcdialogue.Weibo.Util.ParseUtil.getTime
import com.example.abcdialogue.Weibo.Util.ParseUtil.getUri

import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.image_linear_hor.view.new_image_hor
import android.annotation.SuppressLint

import com.example.abcdialogue.R
import com.example.abcdialogue.Weibo.state.LoadStatus
import com.example.abcdialogue.Weibo.Util.ParseUtil.getMiddle2LargeUrl
import com.example.abcdialogue.Weibo.Util.TransfereeFactory.getTransferList

class MyRecyclerAdapter(private var fragment:Fragment,var viewModel: WBViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var onItemClickListener : MyRecyclerAdapter.OnItemClickListener?= null
    var onLoadMoreListener : OnLoadMoreListener?= null

    var onDeleteImageListener : OnDeleteImageListener?= null

    //这里多一项是因为footer
    private var total = 1
    //手机的宽度
    private var winWidth = context.resources.displayMetrics.widthPixels
    //每一项的右边距
    private var itemMarginEnd = DisplayUtil.dp2px(3)

    //每一项的宽度
    private var itemWidth = (winWidth - itemMarginEnd * 3 - 36 - 30) / 3

    var currNumber = 1
    init {
        //这里多写一个是担心，如果在走到这里之前还没有完成第一次请求数据完成了那么
        viewModel.statusList.value?.let {
            total = it.size+1
            var addCounts = (viewModel.page - 1) * 15-it.size
            //如果新增的等于15个 假设还有更多，否则就没有更多了
            hasMore = addCounts >=0
            if (!hasMore){
                viewModel.currStatus.value = LoadStatus.LoadMoreEnd
            }
            Log.i("init adapter observe",viewModel.statusList.value.toString())
        }
        viewModel.statusList.observe(fragment.viewLifecycleOwner,{
            total = it.size+1
            var addCounts = (viewModel.page - 1) * 15-it.size
            hasMore = addCounts >= 0
            //已经加载完了
            if (!hasMore){
                viewModel.currStatus.value = LoadStatus.LoadMoreEnd
            }
            Log.i("init adapter observe",viewModel.statusList.value.toString())
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
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position!=(itemCount-1)){
            (holder as MyRecyclerHolder).apply{
                viewModel.statusList.value?.let { it ->
                    it[position].also {
                        textView.text = it.name
                        textView2.text = getTime(it.createdAt)
                        forwardTextView.text = it.repostsCount.toString()
                        commentTextView.text = it.commentsCount.toString()
                        likeTextView.text = it.attitudesCount.toString()

                        if (it.source.isNotEmpty()) {
                            sourceTextView.visibility = View.VISIBLE
                            source.visibility = View.VISIBLE
                            source.text = context.getString(R.string.from_source)
                            sourceTextView.text = getSource(it.source)
                            //来源文本点击后跳转
                            sourceTextView.setOnClickListener { _ ->
                                var intent = Intent(Intent.ACTION_VIEW, getUri(it.source))
                                fragment.startActivity(intent)
                            }
                        } else {
                            sourceTextView.visibility = View.GONE
                            source.visibility = View.VISIBLE
                            source.text = it.description
                        }
                        //设置文本中的链接支持点击
                        content.movementMethod = LinkMovementMethod.getInstance()
                        if (it.text.isNotEmpty()) {
                            //将微博正文穿进去，获取span
                            content.text = getFormatText(it.text)
                        }
                        //头像
                        FrescoUtil.loadImageAddCircle(headerImage, it.avatarLarge)
                        //正文下面的多图部分
                        bindImages(it, holder, position)
                    }
                }
            }
        } else{
            (holder as MyFooterViewHolder).let { holder ->
                //这里给当前的状态设置观察者，不考虑为LoadMoreIng的情况，另行处理
                viewModel.currStatus.observe(fragment.viewLifecycleOwner,{
                    when(it){
                        LoadStatus.LoadMoreEnd ->(holder).update(it)
                        LoadStatus.LoadMoreSuccess -> (holder).update(it)
                        LoadStatus.LoadMoreError -> (holder ).update(it)
                    }
                })
                if (hasMore){
                    (holder).update(LoadStatus.LoadMoreIn)
                }else{
                    (holder).update(LoadStatus.LoadMoreEnd)
                }
            }


        }
    }


    /**
     * 展示图片
     */
    private fun bindImages(it: WBStatusBean, holder: MyRecyclerHolder,position: Int) {

        val size = it.picUrls.size
        if (size == 0) {
            holder.imageContainer.visibility = View.GONE
        } else {
            holder.imageContainer.visibility = View.VISIBLE
            holder.imageContainer.removeAllViews()
            var count = size / 3
            var reminder = size % 3
            for (i in 0 until count) {
                //填充布局设置高度
                val line = LayoutInflater.from(holder.imageContainer.context)
                    .inflate(R.layout.image_linear_hor, holder.imageContainer, false).apply {
                        this.layoutParams.height = itemWidth
                    }
                for (j in 0 until 3) {
                    val childView = SimpleDraweeView(line.context)
                    line.new_image_hor.addView(childView, LinearLayout.LayoutParams(
                        itemWidth,
                        itemWidth
                    ).apply {
                        rightMargin = itemMarginEnd
                    })
                    //小图使用fresco加载
                    FrescoUtil.loadImageAddSize(childView, it.picUrls[i * 3 + j])
                    val transfer = getTransferList(fragment, it.picUrls.map{
                        getMiddle2LargeUrl(it)
                    }, onDeleteImageListener,position,i * 3 + j)
                    childView.setOnClickListener {
                        transfer.show()
                    }
                }
                //添加到子视图
                holder.imageContainer.addView(line)
            }
            val footLine = LayoutInflater.from(holder.imageContainer.context)
                .inflate(R.layout.image_linear_hor, holder.imageContainer, false).apply {
                    this.layoutParams.height = itemWidth
                }
            if (reminder != 0) {
                for (i in 0 until reminder) {
                    val childView = SimpleDraweeView(footLine.context)
                    //设置宽高和边距
                    footLine.new_image_hor.addView(childView, LinearLayout.LayoutParams(
                        itemWidth,
                        itemWidth
                    ).apply {
                        rightMargin = itemMarginEnd
                    })
                    //小图使用fresco加载
                    FrescoUtil.loadImageAddSize(childView, it.picUrls[count * 3 + i])
                    val transfer = getTransferList(fragment, it.picUrls.map{
                        getMiddle2LargeUrl(it)
                    }, onDeleteImageListener,position,count * 3 + i)
                    //设置点击监听
                    childView.setOnClickListener {
                        transfer.show() //展示
                    }
                }
                //添加到子视图
                holder.imageContainer.addView(footLine)
            }
        }
    }


    override fun getItemCount(): Int {
        return total
    }

    /**
     * 获取Item的类型
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
     fun addItem(pos:Int){
        ++currNumber
        //刷新适配器
        notifyItemInserted(pos)
    }

    fun removeItem(pos:Int){

        //移除该位置的项目
        notifyItemRemoved(pos)
        --currNumber
    }

    fun updateItem(position: Int) {
        //更新某一项，当某一项的数据过期时调用该方法刷新
        notifyItemChanged(position)
        //notifyItemRangeChanged(pos,1)

    }

    //每一项的点击事件
    public interface OnItemClickListener{
        fun onItemClick(view:View,pos:Int)
    }

    //加载更多的监听接口
    public interface OnLoadMoreListener{
        fun onLoadMore(view: MyFooterViewHolder)
    }

    //删除图片的监听接口
    public interface OnDeleteImageListener{
        fun onDeleteImageListener(position: Int, imageUrl: String?)

    }

    companion object {
        const val TYPE_NORMAL = 0
        const val TYPE_LOAD_MORE = 1

        //页码，也可以理解为加载了多少次
        const val PAGESIZE = 15
        //过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0
        const val FEATURE = 2


        /**
         * 是否能加载更多
         * 这个值只在请求后判断是否有数据来设置，增加删除方法专注于内容的删改
         */
        var hasMore = false
    }
}

