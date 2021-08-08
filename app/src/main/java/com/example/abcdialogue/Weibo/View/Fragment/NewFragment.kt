package com.example.abcdialogue.Weibo.View.Fragment

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abcdialogue.Module.MainActivity2
import com.example.abcdialogue.MyApplication
import com.example.abcdialogue.Weibo.Adapter.MyRecyclerAdapter
import com.example.abcdialogue.R
import com.example.abcdialogue.Util.Util.toastShort
import com.example.abcdialogue.Weibo.Adapter.LoadStatus
import com.example.abcdialogue.Weibo.Adapter.MyFooterViewHolder
import kotlinx.android.synthetic.main.fragment_liner_recycler.add_btn
import kotlinx.android.synthetic.main.fragment_liner_recycler.new_rv
import kotlinx.android.synthetic.main.fragment_liner_recycler.refresh_layout
import kotlinx.android.synthetic.main.fragment_liner_recycler.remove_btn
import android.os.Looper
import androidx.core.os.HandlerCompat.postDelayed
import androidx.lifecycle.ViewModelProvider
import com.example.abcdialogue.Weibo.InitSDK
import com.example.abcdialogue.Weibo.InitSDK.Companion.TOKEN
import com.example.abcdialogue.Weibo.VM.CountryViewModel
import com.example.abcdialogue.Weibo.VM.CountryViewModelFactory
import java.util.concurrent.Delayed


class NewFragment: Fragment(R.layout.fragment_liner_recycler) {

    private val viewModel by lazy{
        ViewModelProvider(this, CountryViewModelFactory()).get(CountryViewModel::class.java)
    }

    lateinit var adapter: MyRecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.countryList.observe(this.viewLifecycleOwner,{
            initRecycler()
        })
        initData()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MyRecyclerAdapter(this,viewModel)
        //初始化监听事件
        initListener()
        //初始化下啦刷新控件
        handlerDownPullUpdate(adapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
        //初始化recycler
        //initRecycler()
    }

    private fun initData() {
        TOKEN?.let { it1 -> viewModel.getProvinceList(it1) }
    }


    private fun initRecycler() {
        new_rv.layoutManager = LinearLayoutManager(this.context)
        //new_rv.addItemDecoration(MyDecoration())
        new_rv.adapter = adapter
        //设置每一项的动画
        //new_rv.itemAnimator
    }

    private fun initListener(){
        adapter.onItemClickListener = object : MyRecyclerAdapter.OnItemClickListener {
            override fun onItemClick(view: View,pos:Int) {
                "你点击了第${pos}Item".toastShort(view.context)
                var intent = Intent(MyApplication.context, MainActivity2().javaClass)
                startActivity(intent)
            }
        }
        adapter.onLoadMoreListener = object : MyRecyclerAdapter.OnLoadMoreListener {
            override fun onLoadMore(view: MyFooterViewHolder) {
                "你调用了onLoadMore".toastShort(MyApplication.context)
                //在这里去执行刷新数据的操作，使用retrofit
                Handler(Looper.getMainLooper()).post{
                    for (i in 1..adapter.pageSize){
                        adapter.addItem(adapter.itemCount-1)
                    }
                }
            }
        }

        add_btn.setOnClickListener{
            adapter.addItem(adapter.itemCount-1)
        }

        remove_btn.setOnClickListener{
            adapter.removeItem(adapter?.itemCount-2)
        }

        refresh_layout.setOnRefreshListener {
            //在这里去执行刷新数据的操作，使用retrofit
            Handler().postDelayed(Runnable {
                //刷新列表
                adapter.notifyDataSetChanged()
                //取消动画
                refresh_layout.isRefreshing = false
            },5000)
        }
    }

    private fun handlerDownPullUpdate(adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>){
        //设置可用
        refresh_layout.isEnabled = true
        //加载动画三种颜色轮训
        refresh_layout.setColorSchemeColors(0xff0000,0x00ff00,0x0000ff)
        //refresh_layout.setColorSchemeResources(R.color.colorPrimary);
        //refresh_layout.setProgressBackgroundColorSchemeColor(0x03DAC5);


    }



    override fun onPause() {
        super.onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }

    class MyDecoration() : RecyclerView.ItemDecoration(){
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            //画了一个矩形，就是有点扁
            outRect.set(0,0,0,view.resources.getDimensionPixelOffset(R.dimen.dividerHeight))
        }
    }

}