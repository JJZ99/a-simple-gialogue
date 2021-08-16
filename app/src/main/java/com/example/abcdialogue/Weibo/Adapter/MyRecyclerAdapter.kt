package com.example.abcdialogue.Weibo.Adapter


import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.LinearLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.MyApplication
import com.example.abcdialogue.MyApplication.Companion.context
import com.example.abcdialogue.Weibo.Util.DisplayUtil
import com.example.abcdialogue.Weibo.Util.ToastUtil.toastInfo
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder.Companion.LOADER_STATE_END
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder.Companion.LOADER_STATE_ING
import com.example.abcdialogue.Weibo.Bean.WBStatusBean
import com.example.abcdialogue.Weibo.Model.WBViewModel
import com.example.abcdialogue.Weibo.Util.FrescoUtil
import com.example.abcdialogue.Weibo.Util.ParseUtil.getFormatText
import com.example.abcdialogue.Weibo.Util.ParseUtil.getSource
import com.example.abcdialogue.Weibo.Util.ParseUtil.getTime
import com.example.abcdialogue.Weibo.Util.ParseUtil.getUri
import com.example.abcdialogue.Weibo.WeiBoActivity

import com.facebook.drawee.view.SimpleDraweeView
import kotlinx.android.synthetic.main.image_linear_hor.view.new_image_hor
import android.R
import android.annotation.SuppressLint

import android.graphics.Typeface
import android.content.res.AssetManager








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
    private var itemWidth = (winWidth - itemMarginEnd * 3 - 36 - 30) / 3

    var currNumber = 1
    init {
        //这里多写一个是担心，如果在走到这里之前还没有完成第一次请求数据完成了那么
        viewModel.statusList.value?.let {
            total = it.size+1
            var addCounts = (page - 1) * 15-it.size
            //如果新增的等于15个 假设还有更多，否则就没有更多了
            hasMore = addCounts >=0
            Log.i("init adapter observe",viewModel.statusList.value.toString())
        }
        viewModel.statusList.observe(fragment.viewLifecycleOwner,{
            total = it.size+1
            var addCounts = (page - 1) * 15-it.size
            hasMore = addCounts >= 0
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
    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position!=(itemCount-1)){
            (holder as MyRecyclerHolder).apply{
                viewModel.statusList.value?.let { it ->
                    it[position].also{
                        //设置第三方字体
//                        val assetManager = context.assets
//                        val typeface =
//                            Typeface.createFromAsset(assetManager, "siyuan_normal.otf")
//                        textView.typeface = typeface
//                        textView.setTypeface(typeface)
                        textView.text = it.name
                        textView2.text = getTime(it.createdAt)
                        if (it.source.isNotEmpty()){
                            sourceTextView.visibility = View.VISIBLE
                            source.visibility = View.VISIBLE
                            sourceTextView.text = getSource(it.source)
                            //来源文本点击后跳转
                            sourceTextView.setOnClickListener { _ ->
                                var intent = Intent(Intent.ACTION_VIEW, getUri(it.source))
                                fragment.startActivity(intent)
                            }
                        }else{
                            sourceTextView.visibility = View.GONE
                            source.visibility = View.GONE
                        }
                        content.movementMethod = LinkMovementMethod.getInstance()
                        if (it.text.isNotEmpty()){
                            content.text = getFormatText(it.text)
                        }
                        FrescoUtil.loadImageAddCircle(headerImage,it.avatarLarge)
                        bindImages(it,holder)
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

    /**
     * 展示图片
     */
    private fun bindImages(it: WBStatusBean, holder: MyRecyclerHolder) {
        val size = it.picUrls.size
        if (size == 0) {
            holder.imageContainer.visibility = View.GONE
        } else {
            holder.imageContainer.visibility = View.VISIBLE
            holder.imageContainer.removeAllViews()
            var count = size / 3
            var reminder = size % 3
            for (i in 0 until count) {
                val line = LayoutInflater.from(holder.imageContainer.context)
                    .inflate(com.example.abcdialogue.R.layout.image_linear_hor, holder.imageContainer, false).apply {
                        this.layoutParams.height = itemWidth
                    }
                for (j in 0 until 3) {
                    val childView = SimpleDraweeView(line.context)
                    childView.setOnClickListener {
                        "width${childView.width}\nheight${childView.height}\n$winWidth".toastInfo()
                    }
                    FrescoUtil.loadImageAddSize(childView, it.picUrls[i * 3 + j])
                    line.new_image_hor.addView(childView, LinearLayout.LayoutParams(
                        itemWidth,
                        itemWidth
                    ).apply {
                        rightMargin = itemMarginEnd
                    })
                }
                holder.imageContainer.addView(line)
            }
            val footLine = LayoutInflater.from(holder.imageContainer.context)
                .inflate(com.example.abcdialogue.R.layout.image_linear_hor, holder.imageContainer, false).apply {
                    this.layoutParams.height = itemWidth
                }
            if (reminder != 0) {
                for (i in 0 until reminder) {
                    val childView = SimpleDraweeView(footLine.context)
                    childView.setOnClickListener {
                        "width${childView.width}\nheight${childView.height}\n$winWidth".toastInfo()
                    }

                    FrescoUtil.loadImageAddSize(childView, it.picUrls[count * 3 + i])
                    footLine.new_image_hor.addView(childView, LinearLayout.LayoutParams(
                        itemWidth,
                        itemWidth
                    ).apply {
                        rightMargin = itemMarginEnd
                    })
                }
                holder.imageContainer.addView(footLine)
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

